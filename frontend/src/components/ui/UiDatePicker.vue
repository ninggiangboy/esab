<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { useAttrs } from 'vue'

import { Calendar as CalendarIcon, ChevronLeft, ChevronRight } from 'lucide-vue-next'
import {
  DatePickerAnchor,
  DatePickerCalendar,
  DatePickerCell,
  DatePickerCellTrigger,
  DatePickerContent,
  DatePickerField,
  DatePickerGrid,
  DatePickerGridBody,
  DatePickerGridHead,
  DatePickerGridRow,
  DatePickerHeadCell,
  DatePickerHeader,
  DatePickerHeading,
  DatePickerInput,
  DatePickerNext,
  DatePickerPrev,
  DatePickerRoot,
  DatePickerTrigger,
} from 'radix-vue'

import { calendarDayCellTriggerClass } from '@/components/ui/calendar-day-cell-classes'
import { datePickerPopoverContentClass } from '@/components/ui/date-picker-popover-classes'
import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    id?: string
    defaultValue?: DateValue
    disabled?: boolean
    fixedWeeks?: boolean
    granularity?: 'day' | 'hour' | 'minute' | 'second'
    minValue?: DateValue
    maxValue?: DateValue
    name?: string
    required?: boolean
  }>(),
  { fixedWeeks: true, granularity: 'day' },
)

// eslint-disable-next-line @typescript-eslint/no-explicit-any -- Radix `DateValue` + vue-tsc vs `ZonedDateTime` `#private` breaks strict `v-model` typing
const model = defineModel<any>()

const attrs = useAttrs()

const fieldClass = cn(
  'flex h-8 w-full min-w-0 select-none items-center gap-1.5 overflow-hidden rounded-sm border border-input bg-background-secondary px-2 text-sm shadow-sm',
  'ring ring-inset ring-input focus-within:ring-2 focus-within:ring-primary',
  'data-[disabled]:cursor-not-allowed data-[disabled]:opacity-70 data-[invalid]:border-destructive',
)

const calendarTriggerClass = cn(
  'ml-auto inline-flex size-7 shrink-0 items-center justify-center rounded-md text-muted-foreground',
  'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-inset',
)

const segmentClass = cn(
  'rounded-sm px-0.5 py-0 tabular-nums text-foreground outline-none',
  'focus:bg-accent focus:text-accent-foreground',
  'data-[placeholder]:text-muted-foreground',
)

const navBtnClass = cn(
  'inline-flex size-8 items-center justify-center rounded-md text-foreground',
  'hover:bg-accent hover:text-accent-foreground',
  'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring',
  'disabled:pointer-events-none disabled:opacity-50',
)

const headCellClass = 'rounded-md text-center text-xs font-medium text-muted-foreground'
</script>

<template>
  <DatePickerRoot
    :id="id"
    :default-value="defaultValue"
    v-model="model"
    :disabled="disabled"
    :fixed-weeks="fixedWeeks"
    :granularity="granularity"
    :max-value="maxValue"
    :min-value="minValue"
    :name="name"
    :required="required"
    :class="cn('w-full max-w-md', attrs.class as string | undefined)"
    v-bind="{ ...attrs, class: undefined }"
  >
    <DatePickerAnchor class="block w-full min-w-0">
      <DatePickerField v-slot="{ segments }" :class="fieldClass">
        <div class="flex min-w-0 flex-1 items-center gap-0.5">
          <template v-for="(item, i) in segments" :key="`${i}-${String(item.part)}`">
            <DatePickerInput v-if="item.part === 'literal'" :part="item.part">
              {{ item.value }}
            </DatePickerInput>
            <DatePickerInput v-else :part="item.part" :class="segmentClass">
              {{ item.value }}
            </DatePickerInput>
          </template>
        </div>
        <DatePickerTrigger :class="calendarTriggerClass" aria-label="Open calendar">
          <CalendarIcon class="size-4" />
        </DatePickerTrigger>
      </DatePickerField>
    </DatePickerAnchor>

    <DatePickerContent
      align="start"
      side="bottom"
      :side-offset="4"
      :class="datePickerPopoverContentClass"
    >
      <DatePickerCalendar v-slot="{ weekDays, grid }" class="p-3">
        <DatePickerHeader class="mb-3 flex items-center justify-between gap-2">
          <DatePickerPrev :class="navBtnClass" aria-label="Previous month">
            <ChevronLeft class="size-4" />
          </DatePickerPrev>
          <DatePickerHeading class="text-sm font-medium" />
          <DatePickerNext :class="navBtnClass" aria-label="Next month">
            <ChevronRight class="size-4" />
          </DatePickerNext>
        </DatePickerHeader>
        <div class="flex flex-col gap-4 sm:flex-row sm:gap-4">
          <DatePickerGrid
            v-for="month in grid"
            :key="month.value.toString()"
            class="w-full border-collapse select-none space-y-1"
          >
            <DatePickerGridHead>
              <DatePickerGridRow class="mb-1 grid w-full grid-cols-7">
                <DatePickerHeadCell v-for="day in weekDays" :key="day" :class="headCellClass">
                  {{ day }}
                </DatePickerHeadCell>
              </DatePickerGridRow>
            </DatePickerGridHead>
            <DatePickerGridBody class="grid">
              <DatePickerGridRow
                v-for="(weekDates, weekIndex) in month.rows"
                :key="`week-${weekIndex}`"
                class="grid w-full grid-cols-7"
              >
                <DatePickerCell
                  v-for="weekDate in weekDates"
                  :key="weekDate.toString()"
                  :date="weekDate"
                  class="relative text-center text-sm"
                >
                  <DatePickerCellTrigger
                    :day="weekDate"
                    :month="month.value"
                    :class="calendarDayCellTriggerClass"
                  >
                    <span class="relative z-10">{{ weekDate.day }}</span>
                  </DatePickerCellTrigger>
                </DatePickerCell>
              </DatePickerGridRow>
            </DatePickerGridBody>
          </DatePickerGrid>
        </div>
      </DatePickerCalendar>
    </DatePickerContent>
  </DatePickerRoot>
</template>
