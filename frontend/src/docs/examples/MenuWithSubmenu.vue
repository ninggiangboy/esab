<script setup lang="ts">
import {
  DropdownMenuPortal,
  DropdownMenuSub,
  DropdownMenuSubContent,
  DropdownMenuSubTrigger,
} from 'radix-vue'

import UiButton from '@/components/ui/UiButton.vue'
import {
  dropdownMenuContentClass,
  dropdownMenuSubTriggerClass,
} from '@/components/ui/dropdown-menu-content-classes'
import UiDropdownItem from '@/components/ui/UiDropdownItem.vue'
import UiDropdownMenu from '@/components/ui/UiDropdownMenu.vue'
</script>

<template>
  <UiDropdownMenu>
    <template #trigger>
      <UiButton variant="outline">Actions</UiButton>
    </template>
    <UiDropdownItem>Copy</UiDropdownItem>
    <UiDropdownItem>Cut</UiDropdownItem>
    <UiDropdownItem>Delete</UiDropdownItem>
    <DropdownMenuSub>
      <DropdownMenuSubTrigger :class="dropdownMenuSubTriggerClass"> Share </DropdownMenuSubTrigger>
      <DropdownMenuPortal>
        <DropdownMenuSubContent
          :class="dropdownMenuContentClass"
          :side-offset="8"
          :align-offset="-4"
        >
          <UiDropdownItem>SMS</UiDropdownItem>
          <UiDropdownItem>Twitter</UiDropdownItem>
          <DropdownMenuSub>
            <DropdownMenuSubTrigger :class="dropdownMenuSubTriggerClass"> Email </DropdownMenuSubTrigger>
            <DropdownMenuPortal>
              <DropdownMenuSubContent :class="dropdownMenuContentClass" :side-offset="8">
                <UiDropdownItem>Work</UiDropdownItem>
                <UiDropdownItem>Personal</UiDropdownItem>
              </DropdownMenuSubContent>
            </DropdownMenuPortal>
          </DropdownMenuSub>
        </DropdownMenuSubContent>
      </DropdownMenuPortal>
    </DropdownMenuSub>
  </UiDropdownMenu>
</template>
