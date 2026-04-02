<script setup lang="ts">
import { CheckboxIndicator, CheckboxRoot } from 'radix-vue'
import { Check, Minus } from 'lucide-vue-next'

import { cn } from '@/lib/utils'

import { labelVariants } from './field-variants'

const checked = defineModel<boolean | 'indeterminate'>('checked', { default: false })

withDefaults(
  defineProps<{
    disabled?: boolean
    id?: string
  }>(),
  { disabled: false },
)
</script>

<template>
  <CheckboxRoot
    :id="id"
    v-model:checked="checked"
    :disabled="disabled"
    :class="
      cn(
        'group/checkbox flex items-center gap-x-2 text-sm cursor-pointer',
        'data-[disabled]:cursor-not-allowed data-[disabled]:opacity-70',
        labelVariants(),
        $attrs.class as string | undefined,
      )
    "
  >
    <div
      :class="
        cn(
          'flex size-4 shrink-0 items-center justify-center rounded-[5px] border bg-background-secondary text-white ring-offset-background',
          'group-data-[focus-visible]/checkbox:outline-none group-data-[focus-visible]/checkbox:ring-2 group-data-[focus-visible]/checkbox:ring-primary/40 group-data-[focus-visible]/checkbox:ring-offset-2',
          'group-data-[state=indeterminate]/checkbox:bg-primary group-data-[state=checked]/checkbox:bg-primary group-data-[state=indeterminate]/checkbox:border-black/10 group-data-[state=checked]/checkbox:border-black/10',
          'dark:group-data-[state=indeterminate]/checkbox:border-none dark:group-data-[state=checked]/checkbox:border-none',
          'group-data-[disabled]/checkbox:cursor-not-allowed group-data-[disabled]/checkbox:opacity-80',
        )
      "
    >
      <CheckboxIndicator>
        <Check v-if="checked === true" class="size-3 stroke-[3]" />
        <Minus v-else-if="checked === 'indeterminate'" class="size-3 stroke-[3]" />
      </CheckboxIndicator>
    </div>
    <span v-if="$slots.default" class="select-none">
      <slot />
    </span>
  </CheckboxRoot>
</template>
