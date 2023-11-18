<script setup lang="ts">
import { ref } from 'vue'
import { useAuth0 } from '@auth0/auth0-vue';
const { getAccessTokenSilently } = useAuth0();
const apiMessage = ref("");
const hasResponse = ref(false);

const callApi = async () => {
    const accessToken = await getAccessTokenSilently();
    try {
        const response = await fetch("http://localhost:8080", {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        })
        const data = await response.json();
        apiMessage.value = data;
        hasResponse.value = true;
    } catch (e: any) {
        apiMessage.value = `Error: the server responded with '${e.response?.status}: ${e.response?.statusText}'`;
    }
}
</script>

<template>
    <div>
        <button @click="callApi">Say hi</button>
        <div v-if="hasResponse">{{ apiMessage }}</div>
    </div>
</template>