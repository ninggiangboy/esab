<script setup lang="ts">
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
} from 'radix-vue'
</script>

<template>
  <CalendarRoot v-slot="{ grid, weekDays }" class="w-fit rounded border border-dashed border-border p-2">
    <CalendarHeader class="mb-2 flex items-center justify-between gap-2 text-sm">
      <CalendarPrev>←</CalendarPrev>
      <CalendarHeading />
      <CalendarNext>→</CalendarNext>
    </CalendarHeader>
    <CalendarGrid
      v-for="month in grid"
      :key="month.value.toString()"
      class="w-full text-sm"
    >
      <CalendarGridHead>
        <CalendarGridRow class="grid grid-cols-7">
          <CalendarHeadCell v-for="day in weekDays" :key="day" class="p-1 text-center">
            {{ day }}
          </CalendarHeadCell>
        </CalendarGridRow>
      </CalendarGridHead>
      <CalendarGridBody>
        <CalendarGridRow v-for="(weekDates, i) in month.rows" :key="i" class="grid grid-cols-7">
          <CalendarCell v-for="d in weekDates" :key="d.toString()" :date="d" class="p-0.5 text-center">
            <CalendarCellTrigger :day="d" :month="month.value" />
          </CalendarCell>
        </CalendarGridRow>
      </CalendarGridBody>
    </CalendarGrid>
  </CalendarRoot>
</template>
