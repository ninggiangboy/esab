<script setup lang="ts">
import { TimeFieldInput, TimeFieldRoot, type TimeValue } from 'reka-ui'
import { FieldGroup } from '@/ui/components/field'
import { cn } from '@/ui/lib/utils'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    class?: string
    granularity?: 'hour' | 'minute' | 'second'
    disabled?: boolean
    readonly?: boolean
  }>(),
  { granularity: 'minute', disabled: false, readonly: false },
)

const model = defineModel<TimeValue | undefined>()
</script>

<template>
  <TimeFieldRoot
    v-model="model"
    v-bind="$attrs"
    :disabled="disabled"
    :readonly="readonly"
    :granularity="granularity"
    :class="cn('grid w-full gap-1.5', $props.class)"
  >
    <template #default="{ segments }">
      <FieldGroup class="flex min-h-8 min-w-0 flex-wrap items-center gap-0.5 px-2">
        <template v-for="s in segments" :key="String(s.part)">
          <TimeFieldInput
            :part="s.part"
            class="rounded-sm p-0.5 text-sm tabular-nums outline-none focus:bg-muted data-[placeholder]:text-muted-foreground"
          />
        </template>
      </FieldGroup>
    </template>
  </TimeFieldRoot>
</template>
