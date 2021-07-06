echo Print screenings that start between 10:00 and 15:30
echo ""
echo Request:
echo {
echo '  "beginFrom": "2021-07-30T10:00:00",'
echo '  "beginTo":   "2021-07-30T15:30:00"'
echo }
echo ""
echo Response:
curl -s -X GET \
    -H "Content-Type: application/json" \
    -d "{\"beginFrom\": \"2021-07-30T08:00:00\", \"beginTo\": \"2021-07-30T15:30:00\"}" \
    http://localhost:8080/screenings | ../jq
echo ""
echo User chooses Jaws that start at 11:00
echo ""
echo Request:
echo {
echo '  "title": "Jaws",'
echo '  "from": "2021-07-30T11:00:00",'
echo }
echo ""
echo Response:
curl -s -X GET \
    -H "Content-Type: application/json" \
    -d "{\"title\":\"Jaws\", \"from\":\"2021-07-30T11:00:00\"}" \
    http://localhost:8080/screenings/seats | ../jq
echo ""
echo User wants to reserve a two places for him and his buddy in the 2nd row seat 2 and 3
echo ""
echo Request:
echo {
echo '  "screeningId": 2'
echo '  "firstname": "John",'
echo '  "lastname": "Wick",'
echo '  "seats": ['
echo '      {'
echo '          "ticketType": "ADULT",'
echo '          "row": 2,'
echo '          "col": 2,'
echo '      },'
echo '      {'
echo '          "ticketType": "ADULT",'
echo '          "row": 2,'
echo '          "col": 3,'
echo '      }'
echo '  ]'
echo }
echo ""
echo Reponse:
curl -s -X POST \
    -H "Content-Type: application/json" \
    -d "{\"screeningId\": 2, \"firstname\": \"John\", \"lastname\": \"Wick\", \"seats\": [{\"ticketType\": \"ADULT\", \"row\": 2, \"col\": 2}, {\"ticketType\": \"ADULT\", \"row\": 2, \"col\": 3}]}" \
    http://localhost:8080/reservation | ../jq