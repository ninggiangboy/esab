<script setup lang="ts">
import UiLabel from '@/components/ui/UiLabel.vue'
import UiTextarea from '@/components/ui/UiTextarea.vue'
</script>

<template>
  <div class="grid w-full gap-1.5">
    <UiLabel for="ta-bio">Your bio</UiLabel>
    <UiTextarea id="ta-bio" placeholder="Tell us a little about yourself…" />
  </div>
</template>
