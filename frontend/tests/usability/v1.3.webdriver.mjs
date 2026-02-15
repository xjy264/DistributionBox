import { spawn } from 'node:child_process'
import { createServer } from 'node:http'
import { mkdir, writeFile } from 'node:fs/promises'
import path from 'node:path'

const ELEMENT_KEY = 'element-6066-11e4-a52e-4f735466cecf'
const FRONTEND_PORT = Number(process.env.USABILITY_FRONTEND_PORT || 5173)
const WEBDRIVER_PORT = Number(process.env.USABILITY_WEBDRIVER_PORT || 4444)
const MOCK_PORT = Number(process.env.USABILITY_MOCK_PORT || 19090)
const FRONTEND_BASE = `http://127.0.0.1:${FRONTEND_PORT}`
const WEBDRIVER_BASE = `http://127.0.0.1:${WEBDRIVER_PORT}`

const now = new Date().toISOString().replace(/[:.]/g, '-')
const artifactDir = path.resolve('test-artifacts/usability-v1.3', now)
const runLog = []
const bugs = []
let bugIndex = 1

function log(message) {
  const line = `[${new Date().toISOString()}] ${message}`
  runLog.push(line)
  console.log(line)
}

function addBug(message, priority = 'P1') {
  const bug = `Bug-${bugIndex} [${priority}]: ${message}`
  bugIndex += 1
  bugs.push(bug)
  console.log(bug)
}

async function mkdirSafe(dir) {
  await mkdir(dir, { recursive: true })
}

function startProcess(name, command, args, options = {}) {
  const proc = spawn(command, args, {
    cwd: options.cwd,
    env: options.env,
    stdio: ['ignore', 'pipe', 'pipe']
  })

  proc.stdout.on('data', (chunk) => {
    const text = String(chunk).trim()
    if (text) {
      log(`${name}: ${text}`)
    }
  })

  proc.stderr.on('data', (chunk) => {
    const text = String(chunk).trim()
    if (text) {
      log(`${name} [stderr]: ${text}`)
    }
  })

  proc.on('exit', (code) => {
    log(`${name} exited with code ${code}`)
  })

  return proc
}

async function waitFor(checker, timeoutMs, intervalMs, errorMessage) {
  const started = Date.now()
  let lastError = null
  while (Date.now() - started < timeoutMs) {
    try {
      const result = await checker()
      if (result) {
        return result
      }
    } catch (error) {
      lastError = error
    }
    await new Promise((resolve) => setTimeout(resolve, intervalMs))
  }
  if (lastError) {
    throw new Error(`${errorMessage}. Last error: ${lastError.message}`)
  }
  throw new Error(errorMessage)
}

async function waitForHttpOk(url) {
  return waitFor(
    async () => {
      const res = await fetch(url)
      return res.ok
    },
    45_000,
    500,
    `Timeout waiting for ${url}`
  )
}

async function wdRequest(method, endpoint, body) {
  const res = await fetch(`${WEBDRIVER_BASE}${endpoint}`, {
    method,
    headers: {
      'content-type': 'application/json'
    },
    body: body ? JSON.stringify(body) : undefined
  })
  const data = await res.json()
  if (!res.ok || data.value?.error) {
    const msg = data.value?.message || JSON.stringify(data)
    throw new Error(`WebDriver ${method} ${endpoint} failed: ${msg}`)
  }
  return data.value
}

async function sessionCreate() {
  const value = await wdRequest('POST', '/session', {
    capabilities: {
      alwaysMatch: {
        browserName: 'safari',
        'safari:automaticInspection': false,
        'safari:automaticProfiling': false
      }
    }
  })

  if (value.sessionId) {
    return value.sessionId
  }
  if (value.capabilities) {
    const sessions = await wdRequest('GET', '/sessions')
    if (Array.isArray(sessions) && sessions[0]?.id) {
      return sessions[0].id
    }
  }
  throw new Error('Failed to create WebDriver session')
}

async function getUrl(sessionId) {
  return wdRequest('GET', `/session/${sessionId}/url`)
}

async function setUrl(sessionId, url) {
  await wdRequest('POST', `/session/${sessionId}/url`, { url })
}

async function execute(sessionId, script, args = []) {
  return wdRequest('POST', `/session/${sessionId}/execute/sync`, {
    script,
    args
  })
}

async function findElements(sessionId, selector) {
  const value = await wdRequest('POST', `/session/${sessionId}/elements`, {
    using: 'css selector',
    value: selector
  })
  return Array.isArray(value) ? value : []
}

async function findElement(sessionId, selector) {
  const value = await wdRequest('POST', `/session/${sessionId}/element`, {
    using: 'css selector',
    value: selector
  })
  return value
}

function elementId(element) {
  return element[ELEMENT_KEY]
}

async function clearElement(sessionId, element) {
  await wdRequest('POST', `/session/${sessionId}/element/${elementId(element)}/clear`)
}

async function sendKeys(sessionId, element, text) {
  await wdRequest('POST', `/session/${sessionId}/element/${elementId(element)}/value`, {
    text,
    value: [...text]
  })
}

async function clickElement(sessionId, element) {
  await wdRequest('POST', `/session/${sessionId}/element/${elementId(element)}/click`)
}

async function screenshot(sessionId, filepath) {
  const base64 = await wdRequest('GET', `/session/${sessionId}/screenshot`)
  await writeFile(filepath, Buffer.from(base64, 'base64'))
}

function startMockApiServer() {
  const server = createServer((req, res) => {
    const url = new URL(req.url || '/', `http://127.0.0.1:${MOCK_PORT}`)
    const pathName = url.pathname

    const sendJson = (payload, status = 200) => {
      res.statusCode = status
      res.setHeader('Content-Type', 'application/json; charset=utf-8')
      res.end(JSON.stringify(payload))
    }

    if (req.method === 'POST' && pathName === '/login') {
      sendJson({
        code: 200,
        message: 'success',
        data: {
          id: 1,
          username: 'admin',
          nickname: '管理员',
          role: 'ROLE_ADMIN',
          token: 'mock-token-v1-3',
          menus: []
        }
      })
      return
    }

    if (req.method === 'GET' && pathName === '/box/page') {
      sendJson({
        records: [
          {
            id: 1,
            boxId: 'DB-001',
            station: '一车间',
            area: 'A区',
            boxAddress: '测试站点',
            size: '800x600'
          }
        ],
        total: 1
      })
      return
    }

    if (req.method === 'GET' && pathName === '/user/page') {
      sendJson({
        code: 200,
        message: 'success',
        data: {
          records: [
            {
              id: 1,
              username: 'admin',
              nickname: '管理员',
              email: 'admin@example.com',
              phone: '13800000000',
              role: 'ROLE_ADMIN'
            }
          ],
          total: 1
        }
      })
      return
    }

    if (req.method === 'GET' && pathName.startsWith('/dict/type/')) {
      sendJson([])
      return
    }

    sendJson({ code: 404, message: `mock not found: ${req.method} ${pathName}` }, 404)
  })

  return new Promise((resolve, reject) => {
    server.on('error', reject)
    server.listen(MOCK_PORT, '127.0.0.1', () => resolve(server))
  })
}

async function runScenario(sessionId) {
  log('Step 1: 打开登录页')
  await setUrl(sessionId, `${FRONTEND_BASE}/login`)

  await waitFor(
    async () => {
      const current = await getUrl(sessionId)
      return current.includes('/login')
    },
    15_000,
    300,
    '登录页未打开'
  )

  log('Step 2: 执行登录点击')
  const inputs = await waitFor(
    async () => {
      const list = await findElements(sessionId, '.login input')
      return list.length >= 2 ? list : null
    },
    15_000,
    300,
    '未找到登录输入框'
  )

  await clearElement(sessionId, inputs[0])
  await sendKeys(sessionId, inputs[0], 'admin')
  await clearElement(sessionId, inputs[1])
  await sendKeys(sessionId, inputs[1], '123456')

  const loginButton = await findElement(sessionId, 'button.el-button--primary')
  await clickElement(sessionId, loginButton)

  await waitFor(
    async () => {
      const current = await getUrl(sessionId)
      return current.includes('/box')
    },
    15_000,
    300,
    '登录后未跳转到 /box'
  )

  log('Step 3: 点击侧边栏进入用户管理')
  const clickedUserMenu = await execute(
    sessionId,
    `
      const target = Array.from(document.querySelectorAll('.el-menu-item')).find((item) =>
        (item.textContent || '').includes('用户')
      )
      if (!target) return false
      target.click()
      return true
    `
  )

  if (!clickedUserMenu) {
    throw new Error('未找到侧边栏“用户”菜单')
  }

  await waitFor(
    async () => {
      const current = await getUrl(sessionId)
      return current.includes('/users')
    },
    15_000,
    300,
    '点击用户菜单后未进入 /users'
  )

  const userLoaded = await waitFor(
    async () => {
      const ok = await execute(
        sessionId,
        `
          const text = document.body.innerText || ''
          return text.includes('用户名') && text.includes('admin')
        `
      )
      return ok ? true : null
    },
    15_000,
    500,
    '用户管理页面未加载出预期数据'
  )

  if (!userLoaded) {
    throw new Error('用户管理页面断言失败')
  }

  log('Step 4: 退出并验证受保护路由重定向')
  const clickedLogout = await execute(
    sessionId,
    `
      const target = Array.from(document.querySelectorAll('button')).find((item) =>
        (item.textContent || '').includes('退出')
      )
      if (!target) return false
      target.click()
      return true
    `
  )

  if (!clickedLogout) {
    throw new Error('未找到“退出”按钮')
  }

  await waitFor(
    async () => {
      const current = await getUrl(sessionId)
      return current.includes('/login')
    },
    15_000,
    300,
    '退出后未回到 /login'
  )

  await setUrl(sessionId, `${FRONTEND_BASE}/users`)
  await waitFor(
    async () => {
      const current = await getUrl(sessionId)
      return current.includes('/login')
    },
    15_000,
    300,
    '退出后访问受保护路由未重定向到 /login'
  )
}

async function main() {
  await mkdirSafe(artifactDir)

  log(`Artifacts: ${artifactDir}`)
  let mockServer = null
  let frontendProc = null
  let webdriverProc = null
  let sessionId = ''

  try {
    mockServer = await startMockApiServer()
    log(`Mock API started at http://127.0.0.1:${MOCK_PORT}`)

    frontendProc = startProcess(
      'frontend-dev',
      'npm',
      ['run', 'dev', '--', '--host', '127.0.0.1', '--port', String(FRONTEND_PORT), '--strictPort'],
      {
        cwd: path.resolve('.'),
        env: {
          ...process.env,
          VITE_API_BASE: `http://127.0.0.1:${MOCK_PORT}`
        }
      }
    )

    await waitForHttpOk(`${FRONTEND_BASE}/login`)
    log('Frontend dev server is ready')

    webdriverProc = startProcess('safaridriver', 'safaridriver', ['-p', String(WEBDRIVER_PORT)])

    await waitFor(
      async () => {
        const res = await fetch(`${WEBDRIVER_BASE}/status`)
        return res.ok
      },
      20_000,
      300,
      'safaridriver 未就绪'
    )
    log('WebDriver is ready')

    sessionId = await sessionCreate()
    log(`WebDriver session created: ${sessionId}`)
    await runScenario(sessionId)
    log('Scenario passed')
  } catch (error) {
    const screenshotPath = path.join(artifactDir, 'failure.png')
    if (sessionId) {
      try {
        await screenshot(sessionId, screenshotPath)
        log(`Failure screenshot saved: ${screenshotPath}`)
      } catch (e) {
        log(`Failed to capture screenshot: ${e.message}`)
      }
    }

    addBug(`可用性自动化测试失败：${error.message}`, 'P1')
    log(`Scenario failed: ${error.message}`)
  } finally {
    if (sessionId) {
      try {
        await wdRequest('DELETE', `/session/${sessionId}`)
      } catch (e) {
        log(`Failed to close session: ${e.message}`)
      }
    }

    if (webdriverProc) {
      webdriverProc.kill('SIGTERM')
    }
    if (frontendProc) {
      frontendProc.kill('SIGTERM')
    }
    if (mockServer) {
      await new Promise((resolve) => mockServer.close(resolve))
    }

    const logPath = path.join(artifactDir, 'run.log')
    await writeFile(logPath, `${runLog.join('\n')}\n`, 'utf-8')

    const summaryPath = path.join(artifactDir, 'summary.json')
    await writeFile(
      summaryPath,
      JSON.stringify(
        {
          timestamp: new Date().toISOString(),
          artifactDir,
          passed: bugs.length === 0,
          bugCount: bugs.length,
          bugs
        },
        null,
        2
      ),
      'utf-8'
    )

    log(`Run log saved: ${logPath}`)
    log(`Summary saved: ${summaryPath}`)

    if (bugs.length === 0) {
      console.log('No reproducible bugs found in this run.')
    } else {
      for (const bug of bugs) {
        console.log(bug)
      }
    }
  }
}

main()
  .then(() => {
    if (bugs.length > 0) {
      process.exit(1)
    }
  })
  .catch((error) => {
    console.error(error)
    process.exit(1)
  })
