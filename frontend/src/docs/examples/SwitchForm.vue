<script setup lang="ts">
import { reactive } from 'vue'
import { toast } from 'vue-sonner'

import UiButton from '@/components/ui/UiButton.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiSeparator from '@/components/ui/UiSeparator.vue'
import UiSwitch from '@/components/ui/UiSwitch.vue'

const form = reactive({
  airplaneMode: false,
  wifi: true,
  bluetooth: false,
})

function submit() {
  toast.message('Submitted', {
    description: JSON.stringify(form),
  })
}
</script>

<template>
  <form class="w-full max-w-sm space-y-4" @submit.prevent="submit">
    <div class="flex flex-row justify-between gap-4">
      <UiLabel class="font-normal">Airplane mode</UiLabel>
      <UiSwitch v-model:checked="form.airplaneMode" />
    </div>
    <UiSeparator />
    <div class="flex flex-row justify-between gap-4">
      <UiLabel class="font-normal">Wi‑Fi</UiLabel>
      <UiSwitch v-model:checked="form.wifi" />
    </div>
    <UiSeparator />
    <div class="flex flex-row justify-between gap-4">
      <UiLabel class="font-normal">Bluetooth</UiLabel>
      <UiSwitch v-model:checked="form.bluetooth" />
    </div>
    <UiSeparator />
    <UiButton type="submit">Submit</UiButton>
  </form>
</template>
