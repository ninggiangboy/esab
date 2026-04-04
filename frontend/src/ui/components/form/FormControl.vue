<script setup lang="ts">
import type { ComponentFieldBindingObject } from 'vee-validate'
import type { HTMLAttributes } from 'vue'
import { computed, inject } from 'vue'
import { cn } from '@/ui/lib/utils'
import { FORM_DESCRIPTION_ACTIVE_KEY } from './injectionKeys'
import { useFormField } from './useFormField'

defineOptions({ inheritAttrs: false })

const props = defineProps<{
  class?: HTMLAttributes['class']
  /**
   * When set, the default slot receives safe v-model binds (`name`, `modelValue`,
   * `onUpdate:modelValue`, `onBlur`) plus a11y — **without** `onInput`/`onChange`
   * from vee (those break NumberField, Checkbox, Switch, …).
   * Omit for native text controls and use `v-bind="{ ...componentField, ...slotProps }"`.
   * For typed `v-bind` without casts, use `FormVm` with `generic="…"`.
   */
  componentField?: ComponentFieldBindingObject<unknown>
}>()

const { id, errorMessage, formDescriptionId, formMessageId } = useFormField()
const descriptionActive = inject(FORM_DESCRIPTION_ACTIVE_KEY)

const ariaInvalid = computed(() => (errorMessage.value ? true : undefined))
const ariaDescribedBy = computed(() => {
  const parts: string[] = []
  if (descriptionActive?.value) {
    parts.push(formDescriptionId.value)
  }
  if (errorMessage.value) {
    parts.push(formMessageId.value)
  }
  return parts.length ? parts.join(' ') : undefined
})

const ariaOnly = computed(() => ({
  id: id.value,
  'aria-describedby': ariaDescribedBy.value,
  'aria-invalid': ariaInvalid.value,
}))

const slotProps = computed(() => {
  if (!props.componentField) {
    return ariaOnly.value
  }
  const f = props.componentField
  return {
    name: f.name,
    modelValue: f.modelValue,
    'onUpdate:modelValue': f['onUpdate:modelValue'],
    onBlur: f.onBlur,
    ...ariaOnly.value,
  }
})
</script>

<template>
  <div data-slot="form-control" :class="cn('relative', props.class)">
    <slot v-bind="slotProps" />
  </div>
</template>
