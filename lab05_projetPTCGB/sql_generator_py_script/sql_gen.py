import requests

def generate_sql_from_json(json_data):
    # Start of SQL script
    sql_script = "-- Insert data into the card table\n"
    sql_script += "INSERT INTO card (card_id, crafting_cost, health, image, name, rarity, stage, type) VALUES\n"
    
    # Process each card
    values = []
    for i, card in enumerate(json_data, 1):
        # Handle null values
        crafting_cost = 'NULL' if card.get('craftingCost') is None else card['craftingCost']
        health = 'NULL' if card.get('health') is None else card['health']
        stage = "'N/A'" if card.get('stage') is None else f"'{card['stage']}'"
        
        # Create the value string for this card
        value = f"({i}, {crafting_cost}, {health}, '{card['image']}', '{card['name'].replace("'", "''")}', " \
                f"'{card['rarity']}', {stage}, '{card['type']}')"
        
        values.append(value)
    
    # Join all values with commas and semicolon at the end
    sql_script += ",\n".join(values) + ";"
    
    return sql_script



# URL of the raw JSON data
url = "https://raw.githubusercontent.com/chase-manning/pokemon-tcg-pocket-cards/main/v1.json"

try:
    # Fetch the JSON data from the URL
    response = requests.get(url)
    response.raise_for_status()
    json_data = response.json()

    # Generate the SQL
    sql_output = generate_sql_from_json(json_data)

    # Write to file
    with open('insert_cards.sql', 'w', encoding='utf-8') as file:
        file.write(sql_output)

    print("SQL script has been generated in 'insert_cards.sql'")

except requests.RequestException as e:
    print(f"Error fetching data: {e}")
except Exception as e:
    print(f"An error occurred: {e}")
