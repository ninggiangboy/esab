<script setup lang="ts">
import { Copy, FolderOpen, Menu as MenuIcon, Pencil, Share2, Trash2 } from 'lucide-vue-next'

import UiButton from '@/components/ui/UiButton.vue'
import UiDropdownItem from '@/components/ui/UiDropdownItem.vue'
import UiDropdownMenu from '@/components/ui/UiDropdownMenu.vue'
</script>

<template>
  <UiDropdownMenu>
    <template #trigger>
      <UiButton aria-label="Menu" size="icon" variant="outline">
        <MenuIcon class="size-4" />
      </UiButton>
    </template>
    <UiDropdownItem class="gap-2">
      <FolderOpen class="size-4" />
      Open
    </UiDropdownItem>
    <UiDropdownItem class="gap-2">
      <Pencil class="size-4" />
      Rename
    </UiDropdownItem>
    <UiDropdownItem class="gap-2">
      <Copy class="size-4" />
      Duplicate
    </UiDropdownItem>
    <UiDropdownItem class="gap-2">
      <Share2 class="size-4" />
      Share
    </UiDropdownItem>
    <UiDropdownItem class="gap-2">
      <Trash2 class="size-4" />
      Delete
    </UiDropdownItem>
  </UiDropdownMenu>
</template>
