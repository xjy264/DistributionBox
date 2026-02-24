import { ElMessageBox } from 'element-plus'

export const confirmDeleteAction = async (message = '确认删除该记录？') => {
  try {
    await ElMessageBox.confirm(message, '提示', { type: 'warning' })
    return true
  } catch {
    return false
  }
}
