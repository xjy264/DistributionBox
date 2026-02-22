export const normalizeImageField = (value: unknown): string => {
  if (typeof value !== 'string') return ''
  return value.trim()
}

export const extractUuid = (value: string): string => {
  const raw = value.split('?')[0].split('#')[0]
  const previewMatch = raw.match(/\/files\/preview\/([^/]+)$/)
  if (previewMatch?.[1]) return previewMatch[1]
  const fileMatch = raw.match(/\/files\/([^/]+)$/)
  if (fileMatch?.[1]) return fileMatch[1]
  return ''
}

export const resolvePreviewUrl = (value: unknown): string => {
  const raw = normalizeImageField(value)
  if (!raw) return ''
  const uuid = extractUuid(raw)
  if (uuid) return `/api/files/preview/${uuid}`
  return raw
}

export const isImageUrl = (url?: string): boolean => {
  if (!url) return false
  return /\.(png|jpe?g|gif|webp|bmp|svg)(\?.*)?$/i.test(url)
}
