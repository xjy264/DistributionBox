<template>
  <el-form :model="localValue" label-width="140px">
    <el-form-item v-for="field in fields" :key="field.key" :label="field.label">
      <el-input v-if="!field.type || field.type === 'text'" v-model="localValue[field.key]" />
      <el-input v-else-if="field.type === 'textarea'" type="textarea" :rows="3" v-model="localValue[field.key]" />
      <ImageUpload v-else-if="field.type === 'image'" v-model="localValue[field.key]" />
      <el-date-picker
        v-else-if="field.type === 'date'"
        v-model="localValue[field.key]"
        type="date"
        value-format="YYYY-MM-DD"
        format="YYYY-MM-DD"
        style="width: 100%"
      />
      <el-date-picker
        v-else-if="field.type === 'datetime'"
        v-model="localValue[field.key]"
        type="datetime"
        value-format="YYYY-MM-DD HH:mm:ss"
        format="YYYY-MM-DD HH:mm:ss"
        style="width: 100%"
      />
      <el-select v-else-if="field.type === 'select'" v-model="localValue[field.key]" :placeholder="'请选择' + field.label" style="width: 100%" clearable filterable allow-create>
        <el-option v-for="opt in field.options" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select
        v-else-if="field.type === 'multi-select'"
        v-model="localValue[field.key]"
        :placeholder="'请选择' + field.label"
        style="width: 100%"
        clearable
        filterable
        multiple
      >
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
  value: string | number
}

interface FieldDef {
  key: string
  label: string
  type?: 'text' | 'textarea' | 'image' | 'select' | 'multi-select' | 'date' | 'datetime'
  options?: SelectOption[]
}

const props = defineProps<{ modelValue: Record<string, any>; fields: FieldDef[] }>()
const emit = defineEmits(['update:modelValue'])

const localValue = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})
</script>
