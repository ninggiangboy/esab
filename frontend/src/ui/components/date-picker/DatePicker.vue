<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { getLocalTimeZone, today } from '@internationalized/date'
import {
  DatePickerCalendar,
  DatePickerContent,
  DatePickerField,
  DatePickerInput,
  DatePickerRoot,
  DatePickerTrigger,
} from 'reka-ui'
import { Button } from '@/ui/components/button'
import { FieldGroup } from '@/ui/components/field'
import { cn } from '@/ui/lib/utils'

defineProps<{
  class?: string
}>()

const model = defineModel<DateValue | undefined>({
  default: () => today(getLocalTimeZone()),
})
</script>

<template>
  <DatePickerRoot v-model="model" :class="cn('w-full max-w-[280px] grid gap-1.5', $props.class)">
    <DatePickerField v-slot="{ segments }" class="w-full">
      <FieldGroup class="flex min-h-8 flex-wrap items-center gap-1 px-2">
        <template v-for="item in segments" :key="item.part">
          <DatePickerInput
            :part="item.part"
            class="rounded-sm p-0.5 text-sm tabular-nums outline-none focus:bg-muted data-[placeholder]:text-muted-foreground"
          />
        </template>
      </FieldGroup>
    </DatePickerField>
    <DatePickerTrigger as-child>
      <Button variant="outline" size="sm" class="w-full justify-start">
        Calendar
      </Button>
    </DatePickerTrigger>
    <DatePickerContent class="z-50 rounded-md border bg-popover p-2 shadow-md w-auto">
      <DatePickerCalendar />
    </DatePickerContent>
  </DatePickerRoot>
</template>
