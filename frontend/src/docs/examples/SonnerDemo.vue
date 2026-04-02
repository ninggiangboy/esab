<script setup lang="ts">
import { toast } from 'vue-sonner'

import UiButton from '@/components/ui/UiButton.vue'
</script>

<template>
  <div class="grid max-w-xl grid-cols-2 gap-2 sm:grid-cols-3">
    <UiButton
      variant="outline"
      @click="toast.success('Welcome aboard! 🎉', { description: 'Account created successfully.' })"
    >
      Success
    </UiButton>
    <UiButton
      variant="outline"
      @click="toast.error('Oops! Something went wrong', { description: 'Please try again.' })"
    >
      Error
    </UiButton>
    <UiButton
      variant="outline"
      @click="
        toast.info('New feature available!', { description: 'Check the dashboard for updates.' })
      "
    >
      Info
    </UiButton>
    <UiButton
      variant="outline"
      @click="toast.warning('Storage space running low', { description: '85% storage used.' })"
    >
      Warning
    </UiButton>
    <UiButton
      variant="outline"
      @click="
        toast.message('System maintenance scheduled', {
          description: '2 hours downtime tomorrow at 2 AM.',
        })
      "
    >
      Neutral
    </UiButton>
  </div>
</template>
