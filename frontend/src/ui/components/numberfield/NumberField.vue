<script setup lang="ts">
import { FieldGroup } from '@/ui/components/field'
import { Separator } from '@/ui/components/separator'
import { NumberFieldDecrement, NumberFieldIncrement, NumberFieldInput, NumberFieldRoot } from 'reka-ui'
import { cn } from '@/ui/lib/utils'

defineOptions({ inheritAttrs: false })

defineProps<{
  class?: string
  min?: number
  max?: number
  step?: number
}>()

const model = defineModel<number | null>({ default: null })
</script>

<template>
  <NumberFieldRoot
    v-model="model"
    v-bind="$attrs"
    :min="min"
    :max="max"
    :step="step"
    :class="cn('grid gap-1.5', $props.class)"
  >
    <FieldGroup class="pl-0 pr-0 py-0 overflow-hidden">
      <NumberFieldDecrement class="px-2 text-sm border-r border-input h-full rounded-none bg-transparent hover:bg-muted/50 disabled:opacity-50">
        −
      </NumberFieldDecrement>
      <NumberFieldInput
        class="flex h-full min-w-0 flex-1 bg-transparent px-2 text-sm tabular-nums focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50"
      />
      <Separator orientation="vertical" class="h-full" />
      <NumberFieldIncrement class="px-2 text-sm h-full rounded-none bg-transparent hover:bg-muted/50 disabled:opacity-50">
        +
      </NumberFieldIncrement>
    </FieldGroup>
  </NumberFieldRoot>
</template>
