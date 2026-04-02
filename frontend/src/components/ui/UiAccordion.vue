<script setup lang="ts">
import { AccordionRoot } from 'radix-vue'

withDefaults(
  defineProps<{
    type?: 'single' | 'multiple'
    collapsible?: boolean
  }>(),
  {
    type: 'single',
    collapsible: false,
  },
)

const modelValue = defineModel<string | string[] | undefined>()
</script>

<template>
  <AccordionRoot
    v-model="modelValue"
    :type="type"
    :collapsible="collapsible"
    data-slot="accordion"
    class="w-full"
  >
    <slot />
  </AccordionRoot>
</template>
