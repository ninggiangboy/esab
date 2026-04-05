<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { DateRangePicker } from '@/ui/components/date-range-picker'
import { Button } from '@/ui/components/button'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
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
        <FormVm generic="DateRangeFormValue" v-slot="vm" :component-field="componentField">
          <DateRangePicker v-bind="vm" />
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
