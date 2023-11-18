<script setup lang="ts">
import { useAuth0 } from "@auth0/auth0-vue";
import LoginButton from '@/components/LoginButton.vue';
import LogoutButton from '@/components/LogoutButton.vue';
import TestBackendCall from "@/components/TestBackendCall.vue";

const { user, isAuthenticated, isLoading } = useAuth0(); 
</script>
<template>
  <div class="about">
    <h1>This is an about page</h1>
    <LoginButton></LoginButton>
    <LogoutButton/>
    <div v-if="!isLoading && isAuthenticated">
        <code>{{ user }}</code>
    </div>
    <TestBackendCall />
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
