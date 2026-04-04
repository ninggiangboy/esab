<script setup lang="ts">
import { computed, ref } from 'vue'
import { Button } from '@/ui/components/button'
import { ChevronLeft, ChevronRight } from 'lucide-vue-next'

const pageCount = 10
const page = ref(1)
const canPrev = computed(() => page.value > 1)
const canNext = computed(() => page.value < pageCount)
</script>

<template>
  <div class="flex flex-wrap items-center justify-center gap-1">
    <Button variant="ghost" size="icon" class="size-8" :disabled="!canPrev" @click="page--">
      <ChevronLeft class="size-4" />
    </Button>
    <Button
      v-for="p in pageCount"
      :key="p"
      variant="ghost"
      size="icon"
      class="size-8"
      :class="p === page ? 'border bg-background-secondary shadow-sm text-foreground' : ''"
      @click="page = p"
    >
      {{ p }}
    </Button>
    <Button variant="ghost" size="icon" class="size-8" :disabled="!canNext" @click="page++">
      <ChevronRight class="size-4" />
    </Button>
  </div>
</template>
