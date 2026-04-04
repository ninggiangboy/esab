<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { isSameMonth } from '@internationalized/date'
import {
  CalendarCell,
  CalendarCellTrigger,
  CalendarGrid,
  CalendarGridBody,
  CalendarGridHead,
  CalendarGridRow,
  CalendarHeadCell,
  CalendarHeader,
  CalendarHeading,
  CalendarNext,
  CalendarPrev,
  CalendarRoot,
} from '@/ui/components/calendar'
import { cn } from '@/ui/lib/utils'

const props = withDefaults(
  defineProps<{
    minValue?: DateValue
    maxValue?: DateValue
    unstyled?: boolean
  }>(),
  { unstyled: false },
)

const model = defineModel<DateValue | undefined>()

const triggerClass = (outside: boolean) =>
  cn(
    'flex size-8 items-center justify-center rounded-md p-0 text-sm font-normal transition-colors',
    outside && !props.unstyled && 'text-muted-foreground opacity-60',
    !props.unstyled &&
      'hover:bg-muted focus:bg-muted data-[selected]:bg-primary data-[selected]:text-primary-foreground data-[today]:border data-[today]:border-primary',
  )

function isOutsideMonth(day: DateValue, month: DateValue) {
  return !isSameMonth(day, month)
}
</script>

<template>
  <CalendarRoot
    v-slot="{ grid, weekDays }"
    v-model="model"
    :min-value="minValue"
    :max-value="maxValue"
    fixed-weeks
    :class="cn('w-fit', !unstyled && 'rounded-lg border border-input bg-background-secondary/40 p-3')"
  >
    <CalendarHeader class="mb-2 flex items-center justify-between gap-1">
      <CalendarPrev
        :class="
          cn(
            'inline-flex size-8 items-center justify-center rounded-sm outline-none',
            !unstyled && 'hover:bg-muted focus-visible:ring-2 focus-visible:ring-primary',
          )
        "
      />
      <CalendarHeading class="text-sm font-medium" />
      <CalendarNext
        :class="
          cn(
            'inline-flex size-8 items-center justify-center rounded-sm outline-none',
            !unstyled && 'hover:bg-muted focus-visible:ring-2 focus-visible:ring-primary',
          )
        "
      />
    </CalendarHeader>
    <div class="flex flex-col gap-4 sm:flex-row">
      <CalendarGrid
        v-for="month in grid"
        :key="month.value.toString()"
        :class="cn('w-full border-collapse space-y-1', unstyled && 'border-0 bg-transparent')"
      >
        <CalendarGridHead>
          <CalendarGridRow class="mb-1 flex w-full justify-between">
            <CalendarHeadCell
              v-for="day in weekDays"
              :key="day"
              class="w-9 text-center text-xs font-normal text-muted-foreground"
            >
              {{ day }}
            </CalendarHeadCell>
          </CalendarGridRow>
        </CalendarGridHead>
        <CalendarGridBody class="[&_tr]:flex [&_tr]:w-full [&_tr]:justify-between">
          <CalendarGridRow v-for="(weekDates, wi) in month.rows" :key="`w-${wi}`" class="mt-2 flex w-full">
            <CalendarCell v-for="weekDate in weekDates" :key="weekDate.toString()" :date="weekDate" class="relative p-0 text-center text-sm">
              <CalendarCellTrigger
                :day="weekDate"
                :month="month.value"
                :class="triggerClass(isOutsideMonth(weekDate, month.value))"
              />
            </CalendarCell>
          </CalendarGridRow>
        </CalendarGridBody>
      </CalendarGrid>
    </div>
  </CalendarRoot>
</template>
