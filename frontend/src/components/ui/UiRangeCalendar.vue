<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { useAttrs } from 'vue'

import { ChevronLeft, ChevronRight } from 'lucide-vue-next'
import {
  RangeCalendarCell,
  RangeCalendarCellTrigger,
  RangeCalendarGrid,
  RangeCalendarGridBody,
  RangeCalendarGridHead,
  RangeCalendarGridRow,
  RangeCalendarHeadCell,
  RangeCalendarHeader,
  RangeCalendarHeading,
  RangeCalendarNext,
  RangeCalendarPrev,
  RangeCalendarRoot,
} from 'radix-vue'

import { calendarDayCellTriggerClass } from '@/components/ui/calendar-day-cell-classes'
import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

withDefaults(
  defineProps<{
    disabled?: boolean
    defaultValue?: { start: DateValue | undefined; end: DateValue | undefined }
    fixedWeeks?: boolean
    minValue?: DateValue
    maxValue?: DateValue
  }>(),
  { fixedWeeks: true },
)

// eslint-disable-next-line @typescript-eslint/no-explicit-any -- same DateValue/`v-model` limitation as UiCalendar
const model = defineModel<any>({ default: () => ({ start: undefined, end: undefined }) })
const attrs = useAttrs()

const navBtnClass = cn(
  'inline-flex size-8 items-center justify-center rounded-md text-foreground',
  'hover:bg-accent hover:text-accent-foreground',
  'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring',
  'disabled:pointer-events-none disabled:opacity-50',
)

const headCellClass = 'rounded-md text-center text-xs font-medium text-muted-foreground'
</script>

<template>
  <RangeCalendarRoot
    v-model="model"
    :default-value="defaultValue"
    :disabled="disabled"
    :fixed-weeks="fixedWeeks"
    :max-value="maxValue"
    :min-value="minValue"
    v-slot="{ grid, weekDays }"
    :class="
      cn(
        'w-fit rounded-md border border-border bg-card p-3 text-card-foreground shadow-sm',
        attrs.class as string | undefined,
      )
    "
    v-bind="{ ...attrs, class: undefined }"
  >
    <RangeCalendarHeader class="mb-3 flex items-center justify-between gap-2">
      <RangeCalendarPrev :class="navBtnClass" aria-label="Previous month">
        <ChevronLeft class="size-4" />
      </RangeCalendarPrev>
      <RangeCalendarHeading class="text-sm font-medium" />
      <RangeCalendarNext :class="navBtnClass" aria-label="Next month">
        <ChevronRight class="size-4" />
      </RangeCalendarNext>
    </RangeCalendarHeader>
    <div class="flex flex-col gap-4 sm:flex-row sm:gap-4">
      <RangeCalendarGrid
        v-for="month in grid"
        :key="month.value.toString()"
        class="w-full border-collapse select-none space-y-1"
      >
        <RangeCalendarGridHead>
          <RangeCalendarGridRow class="mb-1 grid w-full grid-cols-7">
            <RangeCalendarHeadCell v-for="day in weekDays" :key="day" :class="headCellClass">
              {{ day }}
            </RangeCalendarHeadCell>
          </RangeCalendarGridRow>
        </RangeCalendarGridHead>
        <RangeCalendarGridBody class="grid">
          <RangeCalendarGridRow
            v-for="(weekDates, weekIndex) in month.rows"
            :key="`week-${weekIndex}`"
            class="grid grid-cols-7"
          >
            <RangeCalendarCell
              v-for="weekDate in weekDates"
              :key="weekDate.toString()"
              :date="weekDate"
              class="relative text-center text-sm"
            >
              <RangeCalendarCellTrigger
                :day="weekDate"
                :month="month.value"
                :class="calendarDayCellTriggerClass"
              >
                <span class="relative z-10">{{ weekDate.day }}</span>
              </RangeCalendarCellTrigger>
            </RangeCalendarCell>
          </RangeCalendarGridRow>
        </RangeCalendarGridBody>
      </RangeCalendarGrid>
    </div>
  </RangeCalendarRoot>
</template>
