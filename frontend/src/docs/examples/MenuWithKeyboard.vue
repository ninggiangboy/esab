<script setup lang="ts">
import UiButton from '@/components/ui/UiButton.vue'
import UiDropdownItem from '@/components/ui/UiDropdownItem.vue'
import UiDropdownMenu from '@/components/ui/UiDropdownMenu.vue'
</script>

<template>
  <UiDropdownMenu>
    <template #trigger>
      <UiButton variant="outline">Shortcuts</UiButton>
    </template>
    <UiDropdownItem class="justify-between gap-6">
      New tab
      <kbd class="text-xs text-muted-foreground">⌘T</kbd>
    </UiDropdownItem>
    <UiDropdownItem class="justify-between gap-6">
      Close tab
      <kbd class="text-xs text-muted-foreground">⌘W</kbd>
    </UiDropdownItem>
  </UiDropdownMenu>
</template>
