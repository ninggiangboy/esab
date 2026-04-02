<script setup lang="ts">
import UiInput from '@/components/ui/UiInput.vue'
import UiLabel from '@/components/ui/UiLabel.vue'
</script>

<template>
  <div class="grid w-full gap-1.5">
    <UiLabel for="in-email">Email</UiLabel>
    <UiInput id="in-email" type="email" placeholder="you@example.com" />
  </div>
</template>
