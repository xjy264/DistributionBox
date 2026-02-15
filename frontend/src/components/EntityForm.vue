<template>
  <el-form :model="localValue" label-width="140px">
    <el-form-item v-for="field in fields" :key="field.key" :label="field.label">
      <el-input v-if="!field.type || field.type === 'text'" v-model="localValue[field.key]" />
      <el-input v-else-if="field.type === 'textarea'" type="textarea" :rows="3" v-model="localValue[field.key]" />
      <ImageUpload v-else-if="field.type === 'image'" v-model="localValue[field.key]" />
      <el-select v-else-if="field.type === 'select'" v-model="localValue[field.key]" :placeholder="'请选择' + field.label" style="width: 100%" clearable filterable allow-create>
        <el-option v-for="opt in field.options" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import ImageUpload from './ImageUpload.vue'

interface SelectOption {
  label: string
  value: string
}

interface FieldDef {
  key: string
  label: string
  type?: 'text' | 'textarea' | 'image' | 'select'
  options?: SelectOption[]
}

const props = defineProps<{ modelValue: Record<string, any>; fields: FieldDef[] }>()
const emit = defineEmits(['update:modelValue'])

const localValue = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})
</script>
