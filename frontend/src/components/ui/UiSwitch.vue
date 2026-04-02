<script setup lang="ts">
import { SwitchRoot, SwitchThumb } from 'radix-vue'

import { cn } from '@/lib/utils'

const checked = defineModel<boolean>('checked', { default: false })

withDefaults(
  defineProps<{
    disabled?: boolean
    id?: string
  }>(),
  { disabled: false },
)
</script>

<template>
  <SwitchRoot
    :id="id"
    v-model:checked="checked"
    :disabled="disabled"
    :class="
      cn(
        'peer inline-flex h-6 w-10 shrink-0 cursor-pointer items-center rounded-full border border-transparent shadow-sm transition-colors',
        'data-[state=checked]:bg-primary data-[state=unchecked]:bg-input',
        'focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2',
        'disabled:cursor-not-allowed disabled:opacity-50',
        $attrs.class as string | undefined,
      )
    "
  >
    <SwitchThumb
      :class="
        cn(
          'pointer-events-none block h-5 w-5 rounded-full bg-background shadow-lg ring-0 transition-transform',
          'data-[state=checked]:translate-x-4 data-[state=unchecked]:translate-x-0',
        )
      "
    />
  </SwitchRoot>
</template>
