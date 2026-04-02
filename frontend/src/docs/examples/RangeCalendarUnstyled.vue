<script setup lang="ts">
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
</script>

<template>
  <RangeCalendarRoot v-slot="{ grid, weekDays }" class="w-fit rounded border border-dashed border-border p-2">
    <RangeCalendarHeader class="mb-2 flex items-center justify-between gap-2 text-sm">
      <RangeCalendarPrev>←</RangeCalendarPrev>
      <RangeCalendarHeading />
      <RangeCalendarNext>→</RangeCalendarNext>
    </RangeCalendarHeader>
    <RangeCalendarGrid
      v-for="month in grid"
      :key="month.value.toString()"
      class="w-full text-sm"
    >
      <RangeCalendarGridHead>
        <RangeCalendarGridRow class="grid grid-cols-7">
          <RangeCalendarHeadCell v-for="day in weekDays" :key="day" class="p-1 text-center">
            {{ day }}
          </RangeCalendarHeadCell>
        </RangeCalendarGridRow>
      </RangeCalendarGridHead>
      <RangeCalendarGridBody>
        <RangeCalendarGridRow v-for="(weekDates, i) in month.rows" :key="i" class="grid grid-cols-7">
          <RangeCalendarCell v-for="d in weekDates" :key="d.toString()" :date="d" class="p-0.5 text-center">
            <RangeCalendarCellTrigger :day="d" :month="month.value" />
          </RangeCalendarCell>
        </RangeCalendarGridRow>
      </RangeCalendarGridBody>
    </RangeCalendarGrid>
  </RangeCalendarRoot>
</template>
