<script setup lang="ts">
import { FieldGroup, Label } from '@/ui/components/field'
import { Input } from '@/ui/components/textfield'
</script>

<template>
  <div class="w-full max-w-xs space-y-2">
    <Label for="f1">Email</Label>
    <FieldGroup>
      <Input id="f1" placeholder="you@example.com" />
    </FieldGroup>
  </div>
</template>
