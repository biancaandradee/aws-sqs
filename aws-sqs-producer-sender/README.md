# Producer

## Clonando projeto
git clone https://github.com/torneseumprogramador/java_aws_sqs_producer_sender.git

## Configurando as variáveis de ambiente
- configurar o .bash_profile ou .bashrc
```shell
code ~/.bash_profile

export AWS_ACCESS_KEY="SUA_ACCESS_KEY"
export AWS_SECRET_KEY="SUA_SECRET_KEY"

source ~/.bash_profile
```

## Rodando o projeto
```shell
./build.sh
./start.sh
```

## Rodando via AWS CLI
```shell
AWS configure
AWS sqs create-queue --queue-name="fila-bianca-andrade" --region=us-east-1
AWS sqs list-queues #Retorna uma lista com as filas existentes
AWS sqs send-message --message-body="Teste" --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade" #Envia uma mensagem para a fila
AWS sqs receive-message --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade" #Retorna as mensagens
AWS sqs receive-message --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade" --wait-time-seconds=20 #Long Polling - economiza request
AWS sqs delete-message --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade" --receipt-handle="AQEBPpMG/QA0aeBlo+zhuvGvfZCrUNE3kAisbZI48bwtkmWuLqXg2vql5z+d+Cqgkv2ryJrpvmn2SiceZkOzl6pYtSx8n+v1KZEMQuAmyQLYrnXR7wlwL0kTDUFoNq9NwMlpYXiifEVhZ1QMI6TWJqFmQEwtgND9emBnF1qs5dgL9gBtmRE5tCOzsuVt0sfPRJAk2JIZoqSeGWDWZEuDgzYBUwGPS+uNjKpzYxoPWWXV15w9qXcgdrFD4esRHFXqrGDZA3ADs6NrMFECsYFzEe3O1yQiVrqc6lDSXnaB3kyO4jh2ybfEcRMxU8hQ8zOX9yB0gNzMCUOk7Mj6X2jpcwmek6pUdUaSGIsBbCPl5PSR6UDGO8SyMts9wD0RvVbxQX1NOPMzzlHRzwP/Z2OWUgWNbw=="  #Deleta uma mensagem
AWS sqs create-queue --queue-name="fila-bianca-andrade-dlq" # Dead-Letter Queue - fila para itens errados não processados/indesejados. Necessário configurar a fila "fila-bianca-andrade" vinculando à DLQ "fila-bianca-andrade-dlq"
AWS sqs delete-queue --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade" #Deleta uma fila
```

## FIFO Queue
- A fila do tipo FIFO (First In - First Out) garante a ordem das mensagens;
- É necessário colocar ".fifo" ao final do nome da fila, no momento da criação;
- Todos as mensagens precisam ser enviadas com o nome do grupo, pois a ordem será retornada de forma categorizada por grupo;
- Criando a fila com o corpo "Content-Based Deduplication", isso nos garante a não duplicação de mensagens enviadas (durante período de 5 min);
- Recomendação: sempre crie uma fila FIFO na qual você garanta que a chave vai ser sempre a mesma para que possa manter a ordem de acordo com o que está sendo enviado. Caso contrário, se enviar uma fila na qual ela já exista, vai dar erro.

## Rodando FIFO via AWS CLI
```shell
AWS configure
AWS sqs create-queue --queue-name="fila-bianca-andrade.fifo" --attributes="{\"FifoQueue\":\"true\", \"ContentBasedDeduplication\":\"true\"}" --region=us-east-1
AWS sqs list-queues
AWS sqs send-message --message-body="Teste2" --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade.fifo" --message-group-id="grupo"
AWS sqs receive-message --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade.fifo" --wait-time-seconds=20 
AWS sqs delete-message --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade.fifo"  --receipt-handle="AQEBPpMG/QA0aeBlo+zhuvGvfZCrUNE3kAisbZI48bwtkmWuLqXg2vql5z+d+Cqgkv2ryJrpvmn2SiceZkOzl6pYtSx8n+v1KZEMQuAmyQLYrnXR7wlwL0kTDUFoNq9NwMlpYXiifEVhZ1QMI6TWJqFmQEwtgND9emBnF1qs5dgL9gBtmRE5tCOzsuVt0sfPRJAk2JIZoqSeGWDWZEuDgzYBUwGPS+uNjKpzYxoPWWXV15w9qXcgdrFD4esRHFXqrGDZA3ADs6NrMFECsYFzEe3O1yQiVrqc6lDSXnaB3kyO4jh2ybfEcRMxU8hQ8zOX9yB0gNzMCUOk7Mj6X2jpcwmek6pUdUaSGIsBbCPl5PSR6UDGO8SyMts9wD0RvVbxQX1NOPMzzlHRzwP/Z2OWUgWNbw=="
AWS sqs delete-queue --queue-url="https://sqs.us-east-1.amazonaws.com/755977887883/fila-bianca-andrade.fifo"
```
