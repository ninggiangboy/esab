<script setup lang="ts">
import { Check, ChevronDown } from 'lucide-vue-next'
import {
  SelectContent,
  SelectItem,
  SelectItemIndicator,
  SelectPortal,
  SelectRoot,
  SelectTrigger,
  SelectValue,
  SelectViewport,
} from 'reka-ui'
import { cn } from '@/ui/lib/utils'

export type BsSelectOption = { id: string | number; name: string }

defineProps<{
  options: BsSelectOption[]
  placeholder?: string
  class?: string
}>()

const model = defineModel<string | undefined>()
</script>

<template>
  <SelectRoot v-model="model">
    <SelectTrigger
      :class="
        cn(
          'flex h-8 w-full items-center justify-between rounded-sm border border-input bg-background-secondary px-3 text-sm outline-none',
          'focus:ring-2 focus:ring-primary disabled:opacity-50',
          $props.class,
        )
      "
    >
      <SelectValue :placeholder="placeholder ?? 'Select'" />
      <ChevronDown class="size-4 opacity-50" />
    </SelectTrigger>
    <SelectPortal>
      <SelectContent class="z-50 overflow-hidden rounded-md border bg-popover p-1 shadow-md">
        <SelectViewport class="max-h-60 p-0.5">
          <SelectItem
            v-for="o in options"
            :key="o.id"
            :value="String(o.id)"
            class="relative flex w-full cursor-pointer select-none items-center rounded-sm py-1.5 pl-8 pr-2 text-sm outline-none data-[highlighted]:bg-accent"
          >
            <span class="absolute left-2 flex size-3.5 items-center justify-center">
              <SelectItemIndicator>
                <Check class="size-4" />
              </SelectItemIndicator>
            </span>
            {{ o.name }}
          </SelectItem>
        </SelectViewport>
      </SelectContent>
    </SelectPortal>
  </SelectRoot>
</template>
