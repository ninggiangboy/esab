<script setup lang="ts">
import { Field } from 'vee-validate'

defineOptions({ inheritAttrs: false })

defineProps<{
  name: string
}>()
</script>

<template>
  <Field v-bind="$attrs" :name="name">
    <template v-if="$slots.default" #default="slotProps">
      <slot v-bind="slotProps" />
    </template>
  </Field>
</template>
