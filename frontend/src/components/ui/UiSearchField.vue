<script setup lang="ts">
import { Search, X } from 'lucide-vue-next'
import { ref } from 'vue'

import { cn } from '@/lib/utils'

import UiFieldGroup from './UiFieldGroup.vue'

const model = defineModel<string>({ default: '' })
const focused = ref(false)
</script>

<template>
  <UiFieldGroup
    class="group flex h-9 items-center gap-1 pl-2 pr-1"
    role="search"
    @focusin="focused = true"
    @focusout="focused = false"
  >
    <Search class="size-4 shrink-0 text-muted-foreground" />
    <input
      v-model="model"
      type="search"
      aria-label="Search"
      :class="
        cn(
          'min-w-0 flex-1 bg-transparent py-1 text-sm outline-none placeholder:text-muted-foreground',
          '[&::-webkit-search-cancel-button]:hidden',
        )
      "
    />
    <button
      v-if="model"
      type="button"
      class="rounded p-1 text-muted-foreground hover:bg-background-tertiary"
      aria-label="Clear"
      @click="model = ''"
    >
      <X class="size-4" />
    </button>
  </UiFieldGroup>
</template>
