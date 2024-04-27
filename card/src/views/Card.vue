<template>
  <div class="game">
    <v-container>
      <!-- Automatic player -->
      <div class="user-info">
        <v-avatar size="60">
          <v-img src="@/assets/zoro.jpg" alt="Automatic Player Avatar"></v-img>
        </v-avatar>
        <div style="color: white;">{{ automaticPlayerName }}</div>
      </div>

      <!-- Automatic player cards -->
      <div class="player-cards">
        <div v-for="(card, index) in automaticPlayerCards" :key="'auto-' + index" class="card" :style="{
            backgroundColor: card.color,
            color: card.color === 'black' ? 'white' : 'black',
            marginLeft: index === 0 ? '0' : '-30px',
            zIndex: automaticPlayerCards.length - index
          }">
          <div class="text"> Number</div>
          <!-- <div class="number">{{ card.number }}</div> -->
          <v-icon class="chip-icon"
            :style="{ color: card.color === 'black' ? 'white' : 'black' }">mdi-cards-outline</v-icon>
        </div>
      </div>
      <div class="score" style="color: white; 
      text-align: right; 
      position: absolute; 
      top: 50px; 
      background-color: #3cb371;
      padding:0 5px 0 5px;
      right: 20px;">{{ automaticPlayerName }} Score: {{ automaticPlayerScore }}</div>
    </v-container>

    <!-- winner information -->

    <div v-if="gameEnded" class="winner-info">
      <template v-if="winner === 'tie'">
        <v-img src="@/assets/tie.gif" alt="Winner Avatar" style="height: 200px;width: 200px;object-fit: cover;"></v-img>
        <div style="color: white; text-align: center;">
          It's a tie!
        </div>
      </template>
      <template v-else-if="winner === 'player'">
        <v-img src="@/assets/luffywin.gif" alt="Winner Avatar"
          style="height: 200px;width: 200px;object-fit: cover;"></v-img>
        <div style="color: white; text-align: center;">
          <h1 style="color: orange;">{{ playerName }} Wins!</h1>
        </div>
      </template>
      <template v-else-if="winner === 'automaticPlayer'">
        <v-img src="@/assets/zorowin.gif" alt="Winner Avatar"
          style="height: 150px;width: 150px;object-fit: cover;"></v-img>
        <div style="color: white; text-align: center;">
          <h1 style="color: orange;">{{ automaticPlayerName }} Wins!</h1>
        </div>
      </template>
    </div>

    <!-- Stack card -->
    <v-container class="stack-open-container">
      <div class="card-stack" @click="drawCardFromStack" v-show="!gameEnded">
        <div class="stacked-card" v-if="cardList.length > 0">
          <div class="text"> Number</div>
          <v-icon class="chip-icon">mdi-cards-outline</v-icon>
        </div>
      </div>

      <!-- Open card -->
      <div class="card opened-card" v-if="openedCard" :class="{ 'animate': showAnimation}" :style="{
          backgroundColor: openedCard.color,
          color: openedCard.color === 'black' ? 'white' : 'black'
        }">
        <div class="text"> Number</div>
        <div class="number">{{ openedCard.number }}</div>
        <v-icon class="chip-icon"
          :style="{ color: openedCard.color === 'black' ? 'white' : 'black' }">mdi-cards-outline</v-icon>
      </div>
    </v-container>

    <v-container>
      <!-- Player cards -->
      <div class="player-cards">
        <div v-for="(card, index) in playerCards" :key="'player-' + index" class="card" :style="{
            backgroundColor: card.color,
            color: card.color === 'black' ? 'white' : 'black',
            marginLeft: index === 0 ? '0' : '-30px',
            zIndex: playerCards.length - index
          }" @click="playerTurn(card)">
          <div class="text"> Number</div>
          <div class="number">{{ card.number }}</div>
          <v-icon class="chip-icon"
            :style="{ color: card.color === 'black' ? 'white' : 'black' }">mdi-cards-outline</v-icon>
        </div>
      </div>

      <!-- Player -->
      <div class="user-info">
        <v-avatar size="60">
          <v-img src="@/assets/luffy.jpg" alt="Logged-in Player Avatar"></v-img>
        </v-avatar>
        <div style="color: white;">{{ playerName }}</div>
      </div>
      <div class="score" style="color: white; 
      text-align: right; 
      position: absolute; 
      background-color: #ff0000;
      padding:0 5px 0 5px;
      top: 20px; 
      right: 20px;"> {{ playerName }} Score: {{ playerScore }}
      </div>
    </v-container>
    <!-- logout -->
    <div style="
        position: fixed;
        bottom:2000 px;
        left: 10px;
      ">
      <v-btn icon @click="reloadPage">
        <v-icon color="white">mdi-refresh</v-icon>
      </v-btn>
      <a class="a-logout" @click="logout()">
        <v-icon color="white">mdi-logout</v-icon></a>
    </div>
  </div>
</template>

<script>
import utils from '@/utils/utils';

export default {
  data() {
    return {
      cardList: [],
      playerCards: [],
      automaticPlayerCards: [],
      playerName: '',
      automaticPlayerName: 'Zoro',
      // showStack: true,
      openedCard: null,
      playerScore: 0,
      automaticPlayerScore: 0,
      currentPlayer:'player',
      winner: '',
      showAnimation:false,
      gameEnded:false
    };
  },
  async created() {
    await this.fetchAllCard();
    this.playerName = this.$route.query.playerName;
    this.shuffleCards();
    this.distributeCards();
    this.openRandomCard();

    console.log("Current Player:", this.currentPlayer);  

    setTimeout(() => {
    this.handleAutoPlayerTurn();
  }, 500); 
  },
  methods: {
    async fetchAllCard() {
      const response = await utils.https.get('/admin/card/list');
      this.cardList = await response.json();
    },
    shuffleCards() {
      for (let i = this.cardList.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [this.cardList[i], this.cardList[j]] = [this.cardList[j], this.cardList[i]];
      }
    },
    distributeCards() {
      for (let i = 0; i < 15; i++) {
        if (this.cardList.length > 0) {
          const drawnCard = this.cardList.shift();
          this.playerCards.push(drawnCard);
        }
      }
      for (let i = 0; i < 15; i++) {
        if (this.cardList.length > 0) {
          const drawnCard = this.cardList.shift();
          this.automaticPlayerCards.push(drawnCard);
        }
      }
    },
    drawCardFromStack() {
      if (this.currentPlayer === 'player') {
        if (this.cardList.length > 0) {
          const drawnCard = this.cardList.shift();
          this.playerCards.push(drawnCard);
          if (!this.canAddToScore(drawnCard)) {
            this.switchPlayerTurn();
          }
        } 
        else {
          this.showStack = false;
          this.checkWinner();
        }
      }
    },
    openRandomCard() {
      if (this.cardList.length > 0) {
        const randomIndex = Math.floor(Math.random() * this.cardList.length);
        this.openedCard = this.cardList.splice(randomIndex, 1)[0];
      }
    },
    playerTurn(card) {
      if (this.currentPlayer === 'player') {
        if (this.canAddToScore(card)) {
          const sum = this.openedCard.number + card.number;
          this.incrementScore('player',sum);
          this.moveCardToMiddle(card);
          this.switchPlayerTurn();
        } 
      }
    },
    canAddToScore(card) {
      if (!this.openedCard) return false;

      const sum = this.openedCard.number + card.number;
      const color = this.openedCard.color === card.color;
      return sum === 10 || sum === 20 || color ;
    },
    incrementScore(playerType, sum) {
      // const color = this.openedCard.color.number+card.color.number;
      if (playerType === 'player' && (sum === 10 || sum === 20)) {
        this.playerScore++;
      } else if (playerType === 'automaticPlayer' && (sum === 10 || sum === 20)) {
        this.automaticPlayerScore++;
      }
    },
    moveCardToMiddle(card) {
      const sum = this.openedCard.number + card.number;
      const color = this.openedCard.color === card.color;

      if (sum === 10 || sum === 20 || color) {
        const index = this.automaticPlayerCards.findIndex(c => c === card);
        if (index !== -1) {
          this.automaticPlayerCards.splice(index, 1);
        }
        const playerIndex = this.playerCards.findIndex(c => c === card);
        if (playerIndex !== -1) {
          this.playerCards.splice(playerIndex, 1);
        }
        this.showAnimation= true;
         this.openedCard = card;
      }
    },
    switchPlayerTurn() {
      if (this.currentPlayer === 'player') {
        this.currentPlayer = 'automaticPlayer';
        this.handleAutoPlayerTurn();
      } else {
        this.currentPlayer = 'player';
        if (this.playerCards.length === 0 || this.automaticPlayerCards.length === 0) {
          this.checkWinner();
        }
        else if (this.cardList.length === 0){
          this.checkWinner();
        }
      }
    },
    async handleAutoPlayerTurn() {
      await this.delay(1000);
      if (this.currentPlayer === 'automaticPlayer') {
        const validMoves = this.getValidMovesForAutoPlayer();
        if (validMoves.length > 0) {
          const randomIndex = Math.floor(Math.random() * validMoves.length);
          const selectedMove = validMoves[randomIndex];
          this.playAutoPlayerMove(selectedMove);
          this.switchPlayerTurn();
        }
         else {
          this.drawCardForAutoPlayer();
          this.switchPlayerTurn();
        }
      }
    },
    getValidMovesForAutoPlayer() {
      const validMoves = [];
      let hasSumResultCard = false;

      for (const card of this.automaticPlayerCards) {
        const sum = this.openedCard.number + card.number;
        if (sum === 10 || sum === 20) {
          validMoves.push(card);
          hasSumResultCard = true;
        }
      }
      if (!hasSumResultCard) {
        for (const card of this.automaticPlayerCards) {
          const color = this.openedCard.color === card.color;
          if (color) {
            validMoves.push(card);
          }
        }
      }
      return validMoves;
    },

    playAutoPlayerMove(move) {
      const index = this.automaticPlayerCards.findIndex(card => card === move);
      if (index !== -1) {
        const playedCard = this.automaticPlayerCards.splice(index, 1)[0];
        const sum = this.openedCard.number + playedCard.number;
        const color = this.openedCard.color === playedCard.color;
        if (sum === 10 || sum === 20 || color) {
          this.moveCardToMiddle(playedCard); 
          this.incrementScore('automaticPlayer',sum);
          this.showAnimation =false; 
          this.openedCard = playedCard; 
        }
      } else {
        console.error("Invalid move:", move);
      }
    },
    drawCardForAutoPlayer() {
      if (this.cardList.length > 0) {
        const randomIndex = Math.floor(Math.random() * this.cardList.length);
        const drawnCard = this.cardList.splice(randomIndex, 1)[0];
        this.automaticPlayerCards.push(drawnCard);
        const sum = this.openedCard.number + drawnCard.number;
        const color = this.openedCard.color === drawnCard.color;// --------------------------
        if (sum === 10 || sum === 20 || color) {
          this.moveCardToMiddle(drawnCard);
        }
      } 
      else{
        this.checkWinner();
      }
    },
    checkWinner() {
      if (this.playerCards.length === 0) {
        this.winner = 'player';
      } else if (this.automaticPlayerCards.length === 0) {
        this.winner = 'automaticPlayer';
      } else if (this.cardList.length === 0) {
        const playerCanAdd = this.playerCards.some(card => this.canAddToScore(card));
        const autoPlayerCanAdd = this.automaticPlayerCards.some(card => this.canAddToScore(card));
        if (!playerCanAdd || !autoPlayerCanAdd) {
          if (this.playerScore > this.automaticPlayerScore) {
            this.winner = 'player';
          } else if (this.automaticPlayerScore > this.playerScore) {
            this.winner = 'automaticPlayer';
          }
        }
      } else if (this.playerScore === this.automaticPlayerScore) {
        this.winner = 'tie';
      }
      this.gameEnded = true;
    },
    logout() {
      this.$store.commit("logout");
      if (this.$route.path != "/login") {
        this.$router.push({ path: "/" });
        this.$router.go();
      }
    },
    reloadPage() {
      window.location.reload();
    },
    // ------------------------------
    async delay(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    }
  }
};
</script>

<style scoped>
.game {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: teal;
  height: fit-content;
  overflow: hidden;
  position: relative;
}
.user-info {
  text-align: center;
  margin-bottom: 10px;
}
.player-cards {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}
@keyframes scaleup {
  from {
    transform: scale(0);
  }
  to {
    transform: scale(1); 
  }
}
.card {
  width: 100px;
  height: 150px;
  border: 2px solid #ccc;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  cursor: pointer;
  animation: scaleup 1s forwards;
}

.card:hover {
  border-color: blue;
}

.card-stack {
  display: flex;
  align-items: center;
  margin-right: 20px;
  cursor: pointer;
}

.stacked-card {
  width: 100px;
  height: 150px;
  border: 2px solid #ccc;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.text {
  font-size: 15px;
}

.number {
  font-size: 24px;
}

.opened-card {
  margin-left: 35%;
}
@keyframes middle-animation {
0% {
    transform: translateY(-50%);
  }
  100% {
    transform: translateY(0);
  }
}
.animate {
  animation: middle-animation 0.5s ease;
}

.stack-open-container {
  display: flex;
}
@media (max-width: 700px) {
  .game {
    padding: 10px; 
  }
  
  .card {
    width: 80px; 
    height: 120px;
  }
  
  .user-info {
    margin-bottom: 5px; 
  }
  
  .player-cards {
    margin-bottom: 5px; 
  }
  
  .card-stack {
    margin-right: 10px;
  }
  
  .stacked-card {
    width: 80px; 
    height: 120px;
  }
  
  .text {
    font-size: 12px; 
  }
  
  .number {
    font-size: 18px; 
  }
    .winner-info {
      position: fixed;
      top: 0;
      left: 0;
      width: 100vw;
      height: 100vh;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 9999;
    }
               
    .a-logout {
      text-decoration: none;
      font-family: "Ubuntu", sans-serif !important;
      font-weight: bolder;
      font-size: small;
        }
        }
</style> 