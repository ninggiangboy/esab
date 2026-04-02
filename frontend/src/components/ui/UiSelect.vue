<script setup lang="ts">
import { computed, useAttrs } from 'vue'

import { Check, ChevronDown, X } from 'lucide-vue-next'
import {
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectItemIndicator,
  SelectItemText,
  SelectLabel,
  SelectPortal,
  SelectRoot,
  SelectTrigger,
  SelectValue,
  SelectViewport,
} from 'radix-vue'

import { selectContentClass } from '@/components/ui/select-content-classes'
import { cn } from '@/lib/utils'

export type UiSelectOption = { value: string; label: string; disabled?: boolean }

export type UiSelectGroup = { label: string; options: UiSelectOption[] }

defineOptions({ inheritAttrs: false })

const props = withDefaults(
  defineProps<{
    /** Flat list of options (use this or `groups`, not both required). */
    options?: UiSelectOption[]
    /** Grouped options (optgroup-style). */
    groups?: UiSelectGroup[]
    placeholder?: string
    disabled?: boolean
    required?: boolean
    name?: string
    id?: string
    autocomplete?: string
    /** Show a clear control when a value is selected (single-select only). */
    clearable?: boolean
  }>(),
  { placeholder: 'Select', options: () => [], groups: undefined },
)

const model = defineModel<string>({ default: '' })

const attrs = useAttrs()

const rootClass = cn('w-full', attrs.class as string | undefined)

const useGroups = computed(() => props.groups != null && props.groups.length > 0)

const showClear = computed(() => Boolean(props.clearable && model.value))

const triggerClass = computed(() =>
  cn(
    'flex h-8 w-full min-w-0 items-center justify-between gap-2 rounded-sm border border-input bg-background-secondary px-3 text-left text-sm shadow-sm',
    'ring ring-inset ring-input focus:outline-none focus:ring-2 focus:ring-primary',
    'data-[placeholder]:text-muted-foreground',
    'disabled:cursor-not-allowed disabled:opacity-80',
    showClear.value && 'pr-14',
  ),
)

const itemClass = cn(
  'relative flex cursor-default select-none items-center rounded-sm py-1.5 pl-2 pr-8 text-sm outline-none transition-colors',
  'focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50',
  'data-[highlighted]:bg-accent data-[highlighted]:text-accent-foreground',
)

const indicatorClass = 'absolute right-2 flex size-4 items-center justify-center text-primary'

function clearSelection() {
  model.value = ''
}
</script>

<template>
  <SelectRoot
    v-model="model"
    :disabled="props.disabled"
    :required="props.required"
    :name="props.name"
    :autocomplete="props.autocomplete"
    :class="rootClass"
    v-bind="{ ...attrs, class: undefined }"
  >
    <div class="relative w-full">
      <SelectTrigger :id="props.id" :class="triggerClass" :disabled="props.disabled">
        <SelectValue :placeholder="props.placeholder" class="min-w-0 flex-1 truncate" />
        <ChevronDown class="size-4 shrink-0 text-muted-foreground opacity-70" aria-hidden="true" />
      </SelectTrigger>
      <button
        v-if="showClear"
        type="button"
        class="absolute right-8 top-1/2 z-[1] flex size-6 -translate-y-1/2 items-center justify-center rounded-sm text-muted-foreground hover:bg-accent hover:text-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring"
        aria-label="Clear selection"
        @pointerdown.stop.prevent="clearSelection"
      >
        <X class="size-3.5" />
      </button>
    </div>

    <SelectPortal>
      <SelectContent position="popper" side="bottom" align="start" :side-offset="4" :class="selectContentClass">
        <SelectViewport class="p-0.5">
          <template v-if="useGroups">
            <template v-for="grp in groups" :key="grp.label">
              <SelectGroup>
                <SelectLabel class="px-2 py-1.5 text-xs font-medium text-muted-foreground">
                  {{ grp.label }}
                </SelectLabel>
                <SelectItem
                  v-for="opt in grp.options"
                  :key="`${grp.label}-${opt.value}`"
                  :value="opt.value"
                  :disabled="opt.disabled"
                  :text-value="opt.label"
                  :class="itemClass"
                >
                  <SelectItemText class="truncate">{{ opt.label }}</SelectItemText>
                  <SelectItemIndicator :class="indicatorClass">
                    <Check class="size-4" />
                  </SelectItemIndicator>
                </SelectItem>
              </SelectGroup>
            </template>
          </template>
          <template v-else>
            <SelectItem
              v-for="opt in options"
              :key="opt.value"
              :value="opt.value"
              :disabled="opt.disabled"
              :text-value="opt.label"
              :class="itemClass"
            >
              <SelectItemText class="truncate">{{ opt.label }}</SelectItemText>
              <SelectItemIndicator :class="indicatorClass">
                <Check class="size-4" />
              </SelectItemIndicator>
            </SelectItem>
          </template>
        </SelectViewport>
      </SelectContent>
    </SelectPortal>
  </SelectRoot>
</template>
