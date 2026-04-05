<script setup lang="ts">
import { computed } from 'vue'
import type { DateValue } from '@internationalized/date'
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
} from 'reka-ui'
import { buttonVariants } from '@/ui/components/button/button-variants'
import { cn } from '@/ui/lib/utils'
import { ChevronLeft, ChevronRight } from 'lucide-vue-next'

const props = withDefaults(
  defineProps<{
    grid: { value: DateValue; rows: DateValue[][] }[]
    weekDays: string[]
    unstyled?: boolean
  }>(),
  { unstyled: false },
)

const isUnstyled = computed(() => props.unstyled)
const weekColIndexes = [0, 1, 2, 3, 4, 5, 6] as const

const navBtnClass = computed(() =>
  cn(
    buttonVariants({ variant: 'ghost', size: 'icon' }),
    !isUnstyled.value &&
      'shrink-0 rounded-full text-primary-foreground hover:bg-muted-foreground/10',
  ),
)

const triggerClass = computed(() =>
  cn(
    buttonVariants({ variant: 'unstyled', size: 'icon' }),
    'flex size-8 items-center justify-center rounded-full p-0 text-sm font-normal transition-none',
    !isUnstyled.value && [
      'cursor-pointer outline-none',
      'data-[disabled]:cursor-default data-[disabled]:text-muted-foreground data-[disabled]:opacity-50',
      'data-[selected]:bg-primary data-[selected]:text-white',
      'data-[focused]:data-[selected]:bg-primary',
      '[&[data-today]:not([data-selected])]:bg-neutral-400/10 [&[data-today]:not([data-selected])]:text-accent-foreground',
      'hover:bg-neutral-400/20 data-[selected]:hover:bg-primary',
      'data-[unavailable]:cursor-default data-[unavailable]:text-destructive-foreground',
      'data-[outside-view]:hidden',
    ],
  ),
)
</script>

<template>
  <CalendarHeader class="flex w-full items-center gap-0.5 pb-1">
    <CalendarHeading class="grow pl-2.5 text-sm font-medium" />
    <div class="flex shrink-0 items-center gap-0.5">
      <CalendarPrev :class="navBtnClass">
        <ChevronLeft class="size-4" aria-hidden="true" />
      </CalendarPrev>
      <CalendarNext :class="navBtnClass">
        <ChevronRight class="size-4" aria-hidden="true" />
      </CalendarNext>
    </div>
  </CalendarHeader>
  <div class="flex flex-col gap-4 sm:flex-row">
    <CalendarGrid
      v-for="month in grid"
      :key="month.value.toString()"
      :class="
        cn(
          'table-fixed border-separate border-spacing-x-0 border-spacing-y-0.5',
          isUnstyled && 'border-0 bg-transparent',
        )
      "
    >
      <colgroup>
        <col v-for="i in weekColIndexes" :key="i" class="w-8 min-w-8 max-w-8" />
      </colgroup>
      <CalendarGridHead>
        <CalendarGridRow>
          <CalendarHeadCell
            v-for="day in weekDays"
            :key="day"
            class="p-0 align-middle font-normal"
          >
            <span
              class="flex size-8 items-center justify-center rounded-md text-[0.8rem] text-muted-foreground"
            >
              {{ day }}
            </span>
          </CalendarHeadCell>
        </CalendarGridRow>
      </CalendarGridHead>
      <CalendarGridBody class="[&>tr>td]:p-0">
        <CalendarGridRow v-for="(weekDates, wi) in month.rows" :key="`w-${wi}`">
          <CalendarCell
            v-for="weekDate in weekDates"
            :key="weekDate.toString()"
            :date="weekDate"
            class="relative w-8 p-0 align-middle text-sm"
          >
            <div class="flex justify-center">
              <CalendarCellTrigger
                :day="weekDate"
                :month="month.value"
                :class="triggerClass"
              />
            </div>
          </CalendarCell>
        </CalendarGridRow>
      </CalendarGridBody>
    </CalendarGrid>
  </div>
</template>
