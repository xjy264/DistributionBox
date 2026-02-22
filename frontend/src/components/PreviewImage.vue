<template>
  <el-image
    v-if="resolvedSrc"
    :src="resolvedSrc"
    :preview-src-list="[resolvedSrc]"
    :style="styleObj"
    :fit="fit"
    preview-teleported
  />
  <span v-else>-</span>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = withDefaults(defineProps<{
  src?: string
  width?: string
  height?: string
  fit?: string
}>(), {
  src: '',
  width: '72px',
  height: '72px',
  fit: 'cover'
})

const resolvedSrc = computed(() => props.src || '')
const styleObj = computed(() => ({ width: props.width, height: props.height, borderRadius: '4px', border: '1px solid #eee' }))
</script>
