<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import DocDateRangePicker from '@/docs/examples/_internal/DocDateRangePicker.vue'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  useForm,
} from '@/ui/components/form'

const schema = toTypedSchema(z.object({ range: z.any() }))
const { handleSubmit } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="range">
      <FormItem>
        <FormLabel>Date range</FormLabel>
        <FormControl v-slot="controlProps">
          <DocDateRangePicker v-bind="{ ...componentField, ...controlProps }" />
        </FormControl>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
