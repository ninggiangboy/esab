<script setup lang="ts">
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDialogStandard from '@/components/ui/UiDialogStandard.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'

const open = ref(false)
</script>

<template>
  <UiDialogStandard v-model="open">
    <template #trigger>
      <UiButton type="button">Sign up</UiButton>
    </template>
    <template #title>Sign up</template>
    <template #description>Please fill out the form below to sign up.</template>
    <div class="flex flex-col gap-4">
      <div class="flex flex-col gap-2">
        <UiLabel for="dlg-fname">First name</UiLabel>
        <UiInput id="dlg-fname" placeholder="First name" autofocus />
      </div>
      <div class="flex flex-col gap-2">
        <UiLabel for="dlg-lname">Last name</UiLabel>
        <UiInput id="dlg-lname" placeholder="Last name" />
      </div>
      <div class="flex justify-end gap-2 pt-2">
        <UiButton variant="outline" type="button" @click="open = false">Cancel</UiButton>
        <UiButton type="button" @click="open = false">Save changes</UiButton>
      </div>
    </div>
  </UiDialogStandard>
</template>
