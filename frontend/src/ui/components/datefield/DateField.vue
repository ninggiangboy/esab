<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { DateFieldInput, DateFieldRoot } from 'reka-ui'
import { FieldGroup } from '@/ui/components/field'
import { cn } from '@/ui/lib/utils'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    class?: string
    granularity?: 'day' | 'hour' | 'minute' | 'second'
  }>(),
  { granularity: 'day' },
)

const model = defineModel<DateValue | undefined>()
</script>

<template>
  <DateFieldRoot
    v-model="model"
    v-bind="$attrs"
    :granularity="granularity"
    :class="cn('grid w-full gap-1.5', $props.class)"
  >
    <template #default="{ segments }">
      <FieldGroup class="flex min-h-8 flex-wrap items-center gap-0.5 px-2">
        <template v-for="s in segments" :key="String(s.part)">
          <DateFieldInput
            :part="s.part"
            class="rounded-sm p-0.5 text-sm tabular-nums outline-none focus:bg-muted data-[placeholder]:text-muted-foreground"
          />
        </template>
      </FieldGroup>
    </template>
  </DateFieldRoot>
</template>
