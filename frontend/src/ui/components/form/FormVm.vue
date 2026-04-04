<script setup lang="ts" generic="TModel = unknown">
import type { ComponentFieldBindingObject } from 'vee-validate'
import type { HTMLAttributes } from 'vue'
import FormControl from './FormControl.vue'
import type { FormControlVModelBinds } from './formControlVmBinds'

/**
 * Thin wrapper around `FormControl` with `:component-field`: applies the internal
 * `FormControlVModelBinds<TModel>` assertion so consumers can `v-bind="vm"` without casts.
 * Set `generic="…"` to the field value type (e.g. `number | null`, `boolean | 'indeterminate'`).
 */
defineOptions({ inheritAttrs: false })

const props = defineProps<{
  componentField: ComponentFieldBindingObject<TModel>
  class?: HTMLAttributes['class']
}>()
</script>

<template>
  <FormControl
    v-bind="$attrs"
    :class="props.class"
    :component-field="props.componentField as ComponentFieldBindingObject<unknown>"
    v-slot="raw"
  >
    <slot v-bind="raw as FormControlVModelBinds<TModel>" />
  </FormControl>
</template>
