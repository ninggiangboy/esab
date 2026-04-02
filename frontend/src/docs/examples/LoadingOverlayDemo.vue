<script setup lang="ts">
import UiButton from '@/components/ui/UiButton.vue'
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
import UiLoadingOverlay from '@/components/ui/UiLoadingOverlay.vue'
</script>

<template>
  <UiLoadingOverlay :is-loading="true">
    <div class="w-full max-w-[350px] space-y-4">
      <div class="space-y-2">
        <div>
          <UiLabel for="ov-email">Email</UiLabel>
          <UiInput id="ov-email" placeholder="Enter your email" />
        </div>
        <div>
          <UiLabel for="ov-pass">Password</UiLabel>
          <UiInput id="ov-pass" type="password" placeholder="Enter your password" />
        </div>
      </div>
      <UiButton class="w-full" type="button">Login</UiButton>
    </div>
  </UiLoadingOverlay>
</template>
