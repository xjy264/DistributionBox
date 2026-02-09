<template>
  <el-form :model="localValue" label-width="140px">
    <el-form-item v-for="field in fields" :key="field.key" :label="field.label">
      <el-input v-if="field.type !== 'textarea'" v-model="localValue[field.key]" />
      <el-input v-else type="textarea" :rows="3" v-model="localValue[field.key]" />
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface FieldDef {
  key: string
  label: string
  type?: 'text' | 'textarea'
}

const props = defineProps<{ modelValue: Record<string, any>; fields: FieldDef[] }>()
const emit = defineEmits(['update:modelValue'])

const localValue = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})
</script>
