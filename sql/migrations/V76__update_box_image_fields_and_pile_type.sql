-- v1.76 配电箱图片字段精简：删除图片3/图片4列
ALTER TABLE sys_box
  DROP COLUMN third_url,
  DROP COLUMN fourth_url;