<script setup lang="ts">
import { computed } from 'vue'
import type { DateValue } from '@internationalized/date'
import { CalendarRoot } from 'reka-ui'
import CalendarPanelGrids from './CalendarPanelGrids.vue'
import { cn } from '@/ui/lib/utils'

const props = withDefaults(
  defineProps<{
    minValue?: DateValue
    maxValue?: DateValue
    variant?: 'default' | 'unstyled'
  }>(),
  { variant: 'default' },
)

const model = defineModel<DateValue | undefined>()

const isUnstyled = computed(() => props.variant === 'unstyled')
</script>

<template>
  <CalendarRoot
    v-slot="{ grid, weekDays }"
    v-model="model"
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
    <CalendarPanelGrids :grid="grid" :week-days="weekDays" :unstyled="isUnstyled" />
  </CalendarRoot>
</template>
