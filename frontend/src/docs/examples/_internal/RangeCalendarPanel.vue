<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { rangeCalendarCellLabelVariants } from '@/ui/components/calendar'
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
} from '@/ui/components/calendar'
import { cn } from '@/ui/lib/utils'
import type { DateRange } from 'reka-ui'

withDefaults(
  defineProps<{
    minValue?: DateValue
    maxValue?: DateValue
    unstyled?: boolean
    defaultValue?: DateRange | null
  }>(),
  { unstyled: false },
)

const model = defineModel<DateRange | null>({ default: null })

function cellVariant(flags: {
  selected: boolean
  selectionStart: boolean
  selectionEnd: boolean
  highlighted: boolean
  highlightedStart: boolean
  highlightedEnd: boolean
}): 'none' | 'middle' | 'cap' {
  if (flags.selectionStart || flags.selectionEnd) return 'cap'
  if (flags.highlightedStart || flags.highlightedEnd) return 'cap'
  if (flags.selected) return 'middle'
  if (flags.highlighted) return 'middle'
  return 'none'
}
</script>

<template>
  <RangeCalendarRoot
    v-slot="{ grid, weekDays }"
    v-model="model"
    :default-value="defaultValue ?? undefined"
    :min-value="minValue"
    :max-value="maxValue"
    fixed-weeks
    :class="
      cn('w-fit', !unstyled && 'rounded-lg border border-input bg-background-secondary/40 p-3')
    "
  >
    <RangeCalendarHeader class="mb-2 flex items-center justify-between gap-1">
      <RangeCalendarPrev
        :class="
          cn(
            'inline-flex size-8 items-center justify-center rounded-sm outline-none',
            !unstyled && 'hover:bg-muted focus-visible:ring-2 focus-visible:ring-primary',
          )
        "
      />
      <RangeCalendarHeading class="text-sm font-medium" />
      <RangeCalendarNext
        :class="
          cn(
            'inline-flex size-8 items-center justify-center rounded-sm outline-none',
            !unstyled && 'hover:bg-muted focus-visible:ring-2 focus-visible:ring-primary',
          )
        "
      />
    </RangeCalendarHeader>
    <div class="flex flex-col gap-4 sm:flex-row">
      <RangeCalendarGrid
        v-for="month in grid"
        :key="month.value.toString()"
        class="w-full border-collapse space-y-1"
      >
        <RangeCalendarGridHead>
          <RangeCalendarGridRow class="mb-1 flex w-full justify-between">
            <RangeCalendarHeadCell
              v-for="day in weekDays"
              :key="day"
              class="w-9 text-center text-xs font-normal text-muted-foreground"
            >
              {{ day }}
            </RangeCalendarHeadCell>
          </RangeCalendarGridRow>
        </RangeCalendarGridHead>
        <RangeCalendarGridBody class="[&_tr]:flex [&_tr]:w-full [&_tr]:justify-between">
          <RangeCalendarGridRow
            v-for="(weekDates, wi) in month.rows"
            :key="`rw-${wi}`"
            class="mt-2 flex w-full"
          >
            <RangeCalendarCell
              v-for="weekDate in weekDates"
              :key="weekDate.toString()"
              :date="weekDate"
              class="relative m-0 h-9 w-9 p-0 text-center text-sm"
            >
              <RangeCalendarCellTrigger :day="weekDate" :month="month.value" class="group relative">
                <template
                  #default="{
                    dayValue,
                    selected,
                    selectionStart,
                    selectionEnd,
                    highlighted,
                    highlightedStart,
                    highlightedEnd,
                  }"
                >
                  <span
                    :class="
                      rangeCalendarCellLabelVariants({
                        selectionState: cellVariant({
                          selected,
                          selectionStart,
                          selectionEnd,
                          highlighted,
                          highlightedStart,
                          highlightedEnd,
                        }),
                      })
                    "
                    >{{ dayValue }}</span
                  >
                </template>
              </RangeCalendarCellTrigger>
            </RangeCalendarCell>
          </RangeCalendarGridRow>
        </RangeCalendarGridBody>
      </RangeCalendarGrid>
    </div>
  </RangeCalendarRoot>
</template>
