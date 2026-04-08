<script setup lang="ts">
import type { DateValue } from '@internationalized/date'
import { BsCalendar } from '@/ui/components/calendar'

function isWeekend(date: DateValue) {
  const day = date.toDate('UTC').getDay()
  return day === 0 || day === 6
}
</script>

<template>
  <BsCalendar>
    <template #day-cell="{ date, dayValue, disabled }">
      <div class="flex flex-col items-center justify-center leading-tight">
        <span>{{ dayValue }}</span>
        <span v-if="!disabled" class="text-[10px] text-muted-foreground">
          {{ isWeekend(date) ? '$120' : '$100' }}
        </span>
      </div>
    </template>
  </BsCalendar>
</template>
