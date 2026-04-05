<script setup lang="ts">
import { BsRangeCalendar, type DateRange } from '@/ui/components/calendar'
import { shallowRef } from 'vue'

/** shallow: range emits immutable date values; deep ref breaks assignability in vue-tsc */
const value = shallowRef<DateRange | null>(null)
</script>

<template>
  <div class="flex flex-col gap-2">
    <BsRangeCalendar v-model="value" />
    <pre class="rounded-md border bg-background-secondary p-2 font-mono text-xs">{{
      value
        ? JSON.stringify({ start: value.start?.toString(), end: value.end?.toString() }, null, 2)
        : 'No value'
    }}</pre>
  </div>
</template>
