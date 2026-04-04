<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { getLocalTimeZone, today } from '@internationalized/date'
import {
  DateRangePickerCalendar,
  DateRangePickerContent,
  DateRangePickerField,
  DateRangePickerInput,
  DateRangePickerRoot,
  DateRangePickerTrigger,
} from 'reka-ui'
import { Button } from '@/ui/components/button'
import { FieldGroup } from '@/ui/components/field'
import { cn } from '@/ui/lib/utils'

defineProps<{
  class?: string
  disabled?: boolean
}>()

const model = defineModel<{ start: DateValue | undefined; end: DateValue | undefined } | undefined>(
  {
    default: () => {
      const t = today(getLocalTimeZone())
      return { start: t, end: t.add({ days: 7 }) }
    },
  },
)
</script>

<template>
  <DateRangePickerRoot
    v-model="model"
    :disabled="disabled"
    :class="cn('grid w-full max-w-[320px] gap-1.5', $props.class)"
  >
    <DateRangePickerField v-slot="{ segments }" class="w-full">
      <FieldGroup class="flex min-h-8 flex-wrap items-center gap-1 px-2">
        <template v-for="item in segments.start" :key="'s-' + String(item.part)">
          <DateRangePickerInput
            type="start"
            :part="item.part"
            class="rounded-sm p-0.5 text-sm tabular-nums outline-none focus:bg-muted data-[placeholder]:text-muted-foreground"
          />
        </template>
        <span class="px-1 text-muted-foreground">–</span>
        <template v-for="item in segments.end" :key="'e-' + String(item.part)">
          <DateRangePickerInput
            type="end"
            :part="item.part"
            class="rounded-sm p-0.5 text-sm tabular-nums outline-none focus:bg-muted data-[placeholder]:text-muted-foreground"
          />
        </template>
      </FieldGroup>
    </DateRangePickerField>
    <DateRangePickerTrigger as-child>
      <Button variant="outline" size="sm" class="w-full justify-start"> Calendar </Button>
    </DateRangePickerTrigger>
    <DateRangePickerContent class="z-50 w-auto rounded-md border bg-popover p-2 shadow-md">
      <DateRangePickerCalendar />
    </DateRangePickerContent>
  </DateRangePickerRoot>
</template>
