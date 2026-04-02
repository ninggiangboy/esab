<script setup lang="ts">
import { DialogClose } from 'radix-vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiSheet from '@/components/ui/UiSheet.vue'
</script>

<template>
  <UiSheet>
    <template #trigger>
      <UiButton type="button">Open sheet</UiButton>
    </template>
    <template #title>Sign up</template>
    <template #description>
      Slide-over panel using the same primitives as the centered dialog.
    </template>
    <div class="flex-1 space-y-4">
      <div class="flex flex-col gap-2">
        <UiLabel for="sh-fname">First name</UiLabel>
        <UiInput id="sh-fname" placeholder="First name" />
      </div>
      <div class="flex flex-col gap-2">
        <UiLabel for="sh-lname">Last name</UiLabel>
        <UiInput id="sh-lname" placeholder="Last name" />
      </div>
    </div>
    <template #footer>
      <DialogClose as-child>
        <UiButton variant="outline" type="button">Cancel</UiButton>
      </DialogClose>
      <DialogClose as-child>
        <UiButton type="button">Save changes</UiButton>
      </DialogClose>
    </template>
  </UiSheet>
</template>
