<script setup lang="ts">
import { toast } from 'vue-sonner'

import ThemeSwitcher from '@/components/ThemeSwitcher.vue'
import UiButton from '@/components/ui/UiButton.vue'
</script>

<template>
  <div class="flex items-center justify-center min-h-screen">
    <div class="text-center max-w-2xl mx-auto px-4">
      <ThemeSwitcher />

      <h1 class="text-4xl md:text-5xl font-bold my-6">Welcome to Tanstack Router</h1>
      <p class="text-lg text-muted-foreground">
        A powerful routing library for React that enables type-safe, flexible, and scalable
        navigation in your applications.
      </p>
      <UiButton
        size="lg"
        class="mt-6"
        @click="
          toast.success('Welcome to Tanstack Router', {
            description:
              'You have successfully launched the starter project. Explore and start building your next great idea!',
          })
        "
      >
        Welcome
      </UiButton>
      <p class="mt-8 text-sm text-muted-foreground">
        <RouterLink to="/docs" class="text-primary underline-offset-4 hover:underline"
          >Documentation</RouterLink
        >
        <span class="mx-2 text-border">·</span>
        <RouterLink to="/dashboard" class="text-primary underline-offset-4 hover:underline"
          >Dashboard</RouterLink
        >
      </p>
    </div>
  </div>
</template>
