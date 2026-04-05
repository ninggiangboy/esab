<script setup lang="ts">
import { computed } from 'vue'
import type { DateValue } from '@internationalized/date'
import type { DateRange } from 'reka-ui'
import { RangeCalendarRoot } from 'reka-ui'
import RangeCalendarPanelGrids from './RangeCalendarPanelGrids.vue'
import { cn } from '@/ui/lib/utils'

const props = withDefaults(
  defineProps<{
    minValue?: DateValue
    maxValue?: DateValue
    variant?: 'default' | 'unstyled'
    defaultValue?: DateRange | null
  }>(),
  { variant: 'default' },
)

const model = defineModel<DateRange | null>({ default: null })

const isUnstyled = computed(() => props.variant === 'unstyled')
</script>

<template>
  <RangeCalendarRoot
    v-slot="{ grid, weekDays }"
    v-model="model"
    :default-value="defaultValue ?? undefined"
    :min-value="minValue"
    :max-value="maxValue"
    :week-starts-on="1"
    fixed-weeks
    :class="
      cn(
        'w-fit',
        !isUnstyled && 'rounded-lg border border-border bg-background-secondary/40 p-1',
      )
    "
  >
    <RangeCalendarPanelGrids :grid="grid" :week-days="weekDays" :unstyled="isUnstyled" />
  </RangeCalendarRoot>
</template>
