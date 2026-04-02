<script setup lang="ts">
import { TabsRoot } from 'radix-vue'

const modelValue = defineModel<string>({ default: '' })
</script>

<template>
  <TabsRoot
    v-model="modelValue"
    class="group flex flex-col gap-2 data-[orientation=vertical]:flex-row"
  >
    <slot />
  </TabsRoot>
</template>
