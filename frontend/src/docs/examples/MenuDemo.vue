<script setup lang="ts">
import { Menu } from 'lucide-vue-next'

import UiButton from '@/components/ui/UiButton.vue'
import UiDropdownItem from '@/components/ui/UiDropdownItem.vue'
import UiDropdownMenu from '@/components/ui/UiDropdownMenu.vue'
</script>

<template>
  <UiDropdownMenu>
    <template #trigger>
      <UiButton aria-label="Menu" size="icon" variant="outline">
        <Menu class="size-4" />
      </UiButton>
    </template>
    <UiDropdownItem>Open</UiDropdownItem>
    <UiDropdownItem>Rename</UiDropdownItem>
    <UiDropdownItem>Duplicate</UiDropdownItem>
  </UiDropdownMenu>
</template>
