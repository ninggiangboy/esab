<script setup lang="ts">
import { useAttrs } from 'vue'

import { ChevronDown } from 'lucide-vue-next'
import {
  ComboboxAnchor,
  ComboboxContent,
  ComboboxEmpty,
  ComboboxInput,
  ComboboxItem,
  ComboboxPortal,
  ComboboxRoot,
  ComboboxTrigger,
  ComboboxViewport,
} from 'radix-vue'

import { cn } from '@/lib/utils'

defineOptions({ inheritAttrs: false })

const props = withDefaults(
  defineProps<{
    options: string[]
    placeholder?: string
    disabled?: boolean
    id?: string
    name?: string
    emptyText?: string
  }>(),
  { placeholder: 'Search…', emptyText: 'No results.' },
)

const model = defineModel<string>({ default: '' })

const attrs = useAttrs()

const rootClass = cn('w-full', attrs.class as string | undefined)

const anchorClass = cn(
  'flex h-8 w-full min-w-0 select-none items-center gap-1 overflow-hidden rounded-sm border border-input bg-background-secondary px-2 text-sm shadow-sm',
  'ring ring-inset ring-input focus-within:ring-2 focus-within:ring-primary',
  'data-[disabled]:cursor-not-allowed data-[disabled]:opacity-70',
)

const inputClass = cn(
  'min-w-0 flex-1 bg-transparent py-1.5 outline-none placeholder:text-muted-foreground md:text-sm',
  'focus:outline-none disabled:cursor-not-allowed disabled:opacity-70',
)

const triggerClass = cn(
  'inline-flex size-7 shrink-0 items-center justify-center rounded-md text-muted-foreground',
  'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-inset',
)

const contentClass = cn(
  'z-50 min-w-[var(--radix-combobox-trigger-width)] max-h-[min(320px,50vh)] overflow-hidden rounded-md border border-border bg-popover p-0 text-popover-foreground shadow-md outline-none',
  'data-[state=open]:animate-in data-[state=closed]:animate-out',
  'data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0',
  'data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95',
  'data-[side=bottom]:slide-in-from-top-2 data-[side=top]:slide-in-from-bottom-2',
  'data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2',
  'duration-200',
)

const itemClass = cn(
  'relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none',
  'data-[disabled]:pointer-events-none data-[disabled]:opacity-50',
  'data-[highlighted]:bg-accent data-[highlighted]:text-accent-foreground',
)
</script>

<template>
  <ComboboxRoot
    v-model="model"
    :disabled="props.disabled"
    :name="props.name"
    :class="rootClass"
    v-bind="{ ...attrs, class: undefined }"
  >
    <ComboboxAnchor :class="anchorClass">
      <ComboboxInput
        :id="props.id"
        :disabled="props.disabled"
        :placeholder="props.placeholder"
        :class="inputClass"
      />
      <ComboboxTrigger
        type="button"
        :class="triggerClass"
        :disabled="props.disabled"
        aria-label="Show suggestions"
      >
        <ChevronDown class="size-4" />
      </ComboboxTrigger>
    </ComboboxAnchor>

    <ComboboxPortal>
      <ComboboxContent
        position="popper"
        side="bottom"
        align="start"
        :side-offset="4"
        :class="contentClass"
      >
        <ComboboxViewport class="max-h-[min(320px,50vh)] scroll-py-1 p-1">
          <ComboboxEmpty class="py-2 text-center text-xs text-muted-foreground">
            {{ props.emptyText }}
          </ComboboxEmpty>
          <ComboboxItem v-for="opt in props.options" :key="opt" :value="opt" :class="itemClass">
            {{ opt }}
          </ComboboxItem>
        </ComboboxViewport>
      </ComboboxContent>
    </ComboboxPortal>
  </ComboboxRoot>
</template>
